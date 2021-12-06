/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysRoleEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserRoleEntity;
import io.renren.modules.sys.service.SysRoleService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import net.sf.jsqlparser.expression.operators.relational.OldOracleJoinBinaryExpression;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 系统用户
 *

 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String username = (String)params.get("username");
		String key = (String)params.get("key");
		String date = (String)params.get("date");
		Long createUserId = (Long)params.get("createUserId");
		List userids = (ArrayList)params.get("userIds");

		IPage<SysUserEntity> page = this.page(
				new Query<SysUserEntity>().getPage(params),
				new QueryWrapper<SysUserEntity>()
						.in(CollectionUtil.isNotEmpty(userids),"user_id",userids)
						.like(StringUtils.isNotBlank(username),"username", username)
						.like(StringUtils.isNotBlank(key),"username", key)
						.like(StringUtils.isNotBlank(date),"create_time", date)
						.eq(createUserId != null,"create_user_id", createUserId)
		);
		List<SysUserEntity> records = page.getRecords();
		records.stream().forEach(x->{
			List<SysUserRoleEntity> list = sysUserRoleService.lambdaQuery().eq(SysUserRoleEntity::getUserId, x.getUserId()).list();
			List<Long> roleList = list.stream().map(y -> y.getRoleId()).collect(Collectors.toList());
			if (CollectionUtil.isNotEmpty(list)) {
				String roleName = "";
				for(Long roleId : roleList){
					SysRoleEntity one = sysRoleService.lambdaQuery().eq(SysRoleEntity::getRoleId, roleId).one();
					if(roleName.equals("")){
						roleName = one.getRoleName();
					}else {
						roleName+=","+one.getRoleName();
					}
				}
				x.setRoleName(roleName);
			}
			x.setRoleIdList(roleList);
		});
		List<SysUserEntity> first = records.stream().filter(x -> x.getRankage() != null).sorted(new Comparator<SysUserEntity>() {
			@Override
			public int compare(SysUserEntity o1, SysUserEntity o2) {
				return o1.getRankage() - o2.getRankage();
			}
		}).collect(Collectors.toList());
		List<SysUserEntity> second = records.stream().filter(x -> x.getRankage() == null).collect(Collectors.toList());
		boolean b = first.addAll(first.size(), second);
		page.setRecords(first);
		return new PageUtils(page);
	}

	@Override
	public List<String> queryAllPerms(Long userId) {
		return baseMapper.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		SysUserEntity sysUserEntity = baseMapper.queryByUserName(username);
		List<SysUserRoleEntity> list = sysUserRoleService.lambdaQuery().eq(SysUserRoleEntity::getUserId, sysUserEntity.getUserId()).list();
		List<Long> roleList = list.stream().map(y -> y.getRoleId()).collect(Collectors.toList());
		sysUserEntity.setRoleIdList(roleList);
		return sysUserEntity;
	}

	@Override
	@Transactional
	public void saveUser(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		this.save(user);

		//检查角色是否越权
		checkRole(user);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		this.updateById(user);

		//检查角色是否越权
		checkRole(user);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	public void deleteBatch(Long[] userId) {
		this.removeByIds(Arrays.asList(userId));
	}

	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setPassword(newPassword);
		return this.update(userEntity,
				new QueryWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
	}

	@Override
	public Map<String, Object> userList(Long current, Long size, String userName) {
		List<SysUserEntity> sysUsers = baseMapper.userList((current-1) * size, size, '%'+userName+'%');
		Integer total = baseMapper.selectCount(
				new QueryWrapper<SysUserEntity>()
						.like("username", userName)
		);
		List<Map<String, Object>> dataList = sysUsers.stream().map(entry -> {
			Map<String, Object> map = new HashMap<>();
			map.put("userId", entry.getUserId());
			map.put("userName", entry.getUsername());
			return map;
		}).collect(Collectors.toList());
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put("current", current);
		returnMap.put("size", dataList.size());
		returnMap.put("total", total);
		returnMap.put("msg", "success");
		returnMap.put("code", 0);
		returnMap.put("dataList", dataList);
		return returnMap;
	}


	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUserEntity user){
		return;
		/*if(user.getRoleIdList() == null || user.getRoleIdList().size() == 0){
			return;
		}
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}

		//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException("新增用户所选角色，不是本人创建");
		}*/
	}
}