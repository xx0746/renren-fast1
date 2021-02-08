package io.renren.modules.performance.service.impl;

import cn.hutool.core.util.StrUtil;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.performance.dao.PerformanceDoctorDao;
import io.renren.modules.performance.entity.PerformanceDoctorEntity;
import io.renren.modules.performance.service.PerformanceDoctorService;


@Service("performanceDoctorService")
public class PerformanceDoctorServiceImpl extends ServiceImpl<PerformanceDoctorDao, PerformanceDoctorEntity> implements PerformanceDoctorService {

    @Autowired
    private SysUserService userService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String status = (String)params.get("status");
        Long userId = (Long)params.get("userId");
        IPage<PerformanceDoctorEntity> page = this.page(
                new Query<PerformanceDoctorEntity>().getPage(params),
                new QueryWrapper<PerformanceDoctorEntity>()
                .eq(StrUtil.isNotEmpty(status),"status",status)
                .eq(userId!=null,"user_id",userId)
        );
        List<PerformanceDoctorEntity> records = page.getRecords();
        records.stream().forEach(x->{
            SysUserEntity one = userService.lambdaQuery().eq(SysUserEntity::getUserId, x.getUserId()).one();
            x.setUserName(one.getUsername());
        });
        page.setRecords(records);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByStatus(List<Integer> status) {
        HashMap<String, Object> map = new HashMap<>();
        IPage<PerformanceDoctorEntity> page = this.page(
                new Query<PerformanceDoctorEntity>().getPage(map),
                new QueryWrapper<PerformanceDoctorEntity>()
                        .in("status",status)
        );
        List<PerformanceDoctorEntity> records = page.getRecords();
        records.stream().forEach(x->{
            SysUserEntity one = userService.lambdaQuery().eq(SysUserEntity::getUserId, x.getUserId()).one();
            x.setUserName(one.getUsername());
        });
        page.setRecords(records);
        return new PageUtils(page);
    }

}