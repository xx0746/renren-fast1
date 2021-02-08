package io.renren.modules.performance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.performance.dao.PerformanceDao;
import io.renren.modules.performance.entity.PerformanceEntity;
import io.renren.modules.performance.service.PerformanceService;

import javax.validation.constraints.NotBlank;


@Service("performanceService")
public class PerformanceServiceImpl extends ServiceImpl<PerformanceDao, PerformanceEntity> implements PerformanceService {

    @Autowired
    private SysUserService userService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long userId = (Long)params.get("userId");
        Integer status = (Integer)params.get("status");
        IPage<PerformanceEntity> page = this.page(
                new Query<PerformanceEntity>().getPage(params),
                new QueryWrapper<PerformanceEntity>()
                        .eq(userId!=null,"user_id",userId)
                        .eq(status!=null,"status",status)
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByIds(List<Long> useIds) {
        HashMap<String, Object> map = new HashMap<>();
        IPage<PerformanceEntity> page = this.page(
                new Query<PerformanceEntity>().getPage(map),
                new QueryWrapper<PerformanceEntity>()
                        .in(CollectionUtil.isNotEmpty(useIds),"user_id",useIds)
        );
        List<PerformanceEntity> records = page.getRecords();
        for (PerformanceEntity record : records) {
            Long userId = record.getUserId();
            SysUserEntity one = userService.lambdaQuery().eq(SysUserEntity::getUserId, userId).one();
            String username = one.getUsername();
            record.setUserName(username);
        }
        page.setRecords(records);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByIdsandStatus(List<Long> useIds, Integer status) {
        HashMap<String, Object> map = new HashMap<>();
        IPage<PerformanceEntity> page = this.page(
                new Query<PerformanceEntity>().getPage(map),
                new QueryWrapper<PerformanceEntity>()
                        .in(CollectionUtil.isNotEmpty(useIds),"user_id",useIds)
                        .eq("status",status)
        );
        List<PerformanceEntity> records = page.getRecords();
        for (PerformanceEntity record : records) {
            Long userId = record.getUserId();
            SysUserEntity one = userService.lambdaQuery().eq(SysUserEntity::getUserId, userId).one();
            String username = one.getUsername();
            record.setUserName(username);
        }
        page.setRecords(records);
        return new PageUtils(page);
    }

    @Override
    public void autorange() {
        List<PerformanceEntity> list = this.lambdaQuery().list();
        List<PerformanceEntity> collect = list.stream().sorted(new Comparator<PerformanceEntity>() {
            @Override
            public int compare(PerformanceEntity o1, PerformanceEntity o2) {
                return o2.getExamine() - o1.getExamine();
            }
        }).collect(Collectors.toList());
        for (int i = 0; i < collect.size(); i++) {
            PerformanceEntity performanceEntity = collect.get(i);
            this.lambdaUpdate().set(PerformanceEntity::getRankage, i + 1).eq(PerformanceEntity::getId, performanceEntity.getId()).update();
        }
    }


}