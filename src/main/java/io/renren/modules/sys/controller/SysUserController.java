/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.SysUserListener;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.performance.service.PerformanceService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysRoleEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserRoleEntity;
import io.renren.modules.sys.form.PasswordForm;
import io.renren.modules.sys.service.SysRoleService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private PerformanceService performanceService;


	/**
	 * 所有用户列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
//		if(getUserId() != Constant.SUPER_ADMIN){
//			params.put("createUserId", getUserId());
//		}
		SysUserEntity user = getUser();
		List<SysUserRoleEntity> list = sysUserRoleService.lambdaQuery().eq(SysUserRoleEntity::getUserId, user.getUserId()).list();
		List<Long> roleIdList = list.stream().map(x -> x.getRoleId()).collect(Collectors.toList());
		if (CollectionUtil.isEmpty(roleIdList)) {

		} else if (roleIdList.contains(new Long("1"))) {
			if (getUserId() != Constant.SUPER_ADMIN) {
				params.put("role", "1");
			}
		}else if (roleIdList.contains(new Long("9"))) {
			if (getUserId() != Constant.SUPER_ADMIN) {
				List<Long> userIds = new ArrayList<>();
				userIds.add(getUserId());
				params.put("userIds", userIds);
			}
		}
		else if (roleIdList.contains(new Long("10"))) {
			if (getUserId() != Constant.SUPER_ADMIN) {
				List<Long> userIds = new ArrayList<>();
				userIds.add(getUserId());
				params.put("userIds", userIds);
			}
		}else if (roleIdList.contains(new Long("2"))) {
			List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 10).list();
			List<Long> userIds = list1.stream().map(x -> x.getUserId()).collect(Collectors.toList());
			if (getUserId() != Constant.SUPER_ADMIN) {
				params.put("userIds", userIds);
			}
		}else if (roleIdList.contains(new Long("11"))) {
			List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 12).list();
			List<Long> userIds = list1.stream().map(x -> x.getUserId()).collect(Collectors.toList());
			if (getUserId() != Constant.SUPER_ADMIN) {
				params.put("userIds", userIds);
			}
		}
		else if (roleIdList.contains(new Long("12"))) {
			if (getUserId() != Constant.SUPER_ADMIN) {
				List<Long> userIds = new ArrayList<>();
				userIds.add(getUserId());
				params.put("userIds", userIds);
			}
		}else if (roleIdList.contains(new Long("13"))) {
			List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 14).list();
			List<Long> userIds = list1.stream().map(x -> x.getUserId()).collect(Collectors.toList());
			if (getUserId() != Constant.SUPER_ADMIN) {
				params.put("userIds", userIds);
			}
		}
		else if (roleIdList.contains(new Long("14"))) {
			if (getUserId() != Constant.SUPER_ADMIN) {
				List<Long> userIds = new ArrayList<>();
				userIds.add(getUserId());
				params.put("userIds", userIds);
			}
		}else if (roleIdList.contains(new Long("15"))) {
			List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 16).list();
			List<Long> userIds = list1.stream().map(x -> x.getUserId()).collect(Collectors.toList());
			if (getUserId() != Constant.SUPER_ADMIN) {
				params.put("userIds", userIds);
			}
		}
		else if (roleIdList.contains(new Long("16"))) {
			if (getUserId() != Constant.SUPER_ADMIN) {
				List<Long> userIds = new ArrayList<>();
				userIds.add(getUserId());
				params.put("userIds", userIds);
			}
		}else if (roleIdList.contains(new Long("17"))) {
			List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 18).list();
			List<Long> userIds = list1.stream().map(x -> x.getUserId()).collect(Collectors.toList());
			if (getUserId() != Constant.SUPER_ADMIN) {
				params.put("userIds", userIds);
			}
		}else if (roleIdList.contains(new Long("18"))) {
			if (getUserId() != Constant.SUPER_ADMIN) {
				List<Long> userIds = new ArrayList<>();
				userIds.add(getUserId());
				params.put("userIds", userIds);
			}
		}else if (roleIdList.contains(new Long("19"))) {
			List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 20).list();
			List<Long> userIds = list1.stream().map(x -> x.getUserId()).collect(Collectors.toList());
			if (getUserId() != Constant.SUPER_ADMIN) {
				params.put("userIds", userIds);
			}
		}
		else if (roleIdList.contains(new Long("20"))) {
			if (getUserId() != Constant.SUPER_ADMIN) {
				List<Long> userIds = new ArrayList<>();
				userIds.add(getUserId());
				params.put("userIds", userIds);
			}
		}else if (roleIdList.contains(new Long("21"))) {
			List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 22).list();
			List<Long> userIds = list1.stream().map(x -> x.getUserId()).collect(Collectors.toList());
			if (getUserId() != Constant.SUPER_ADMIN) {
				params.put("userIds", userIds);
			}
		}else if (roleIdList.contains(new Long("22"))) {
			if (getUserId() != Constant.SUPER_ADMIN) {
				List<Long> userIds = new ArrayList<>();
				userIds.add(getUserId());
				params.put("userIds", userIds);
			}
		}
		PageUtils page = sysUserService.queryPage(params);

		return R.ok().put("page", page);
	}
	/**
	 * 导出用户信息
	 */
	@GetMapping("/export")
	public void export(HttpServletResponse response) throws IOException {
		List<SysUserEntity> users = sysUserService.lambdaQuery().list();
		users.stream().forEach(x->{
			List<SysUserRoleEntity> list = sysUserRoleService.lambdaQuery().eq(SysUserRoleEntity::getUserId, x.getUserId()).list();
			if (CollectionUtil.isNotEmpty(list)) {
				Long roleId = list.get(0).getRoleId();
				SysRoleEntity one = sysRoleService.lambdaQuery().eq(SysRoleEntity::getRoleId, roleId).one();
				x.setRoleName(one.getRoleName());
			}
		});
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
		String fileName = URLEncoder.encode("用户列表", "UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		EasyExcel.write(response.getOutputStream(), SysUserEntity.class).sheet("列表").doWrite(users);
	}
	/**
	 * 上传用户信息
	 */
	@PostMapping("/uploadExcel")
	public R uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
		EasyExcel.read(file.getInputStream(), SysUserEntity.class, new SysUserListener(sysUserService)).sheet().doRead();
		return R.ok();
	}

	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public R info(){
		SysUserEntity user = getUser();
		List<SysUserRoleEntity> list = sysUserRoleService.lambdaQuery().eq(SysUserRoleEntity::getUserId, user.getUserId()).list();
		if (CollectionUtil.isNotEmpty(list)) {
			Long roleId = list.get(0).getRoleId();
			SysRoleEntity one = sysRoleService.lambdaQuery().eq(SysRoleEntity::getRoleId, roleId).one();
			user.setRoleName(one.getRoleName());
		}
		return R.ok().put("user", user);
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@PostMapping("/password")
	public R password(@RequestBody PasswordForm form){
		Assert.isBlank(form.getNewPassword(), "新密码不为能空");
		
		//sha256加密
		String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
		//sha256加密
		String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();
				
		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.getById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		user.setCreateUserId(getUserId());
		sysUserService.saveUser(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@PostMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		user.setCreateUserId(getUserId());
		sysUserService.update(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
	}
}
