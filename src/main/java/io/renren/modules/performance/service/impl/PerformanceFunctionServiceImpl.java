package io.renren.modules.performance.service.impl;

import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.performance.dao.PerformanceFunctionDao;
import io.renren.modules.performance.entity.PerformanceFunctionEntity;
import io.renren.modules.performance.service.PerformanceFunctionService;


@Service("performanceFunctionService")
public class PerformanceFunctionServiceImpl extends ServiceImpl<PerformanceFunctionDao, PerformanceFunctionEntity> implements PerformanceFunctionService {

    @Autowired
    private SysUserService userService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long userId = (Long)params.get("userId");
        Integer status = (Integer) params.get("status");
        IPage<PerformanceFunctionEntity> page = this.page(
                new Query<PerformanceFunctionEntity>().getPage(params),
                new QueryWrapper<PerformanceFunctionEntity>()
                        .eq(userId!=null,"user_id",userId)
                        .eq(status!=null,"status",status)

        );
        List<PerformanceFunctionEntity> records = page.getRecords();
        records.forEach(x->{
            SysUserEntity one = userService.lambdaQuery().eq(SysUserEntity::getUserId, x.getUserId()).one();
            x.setUserName(one.getUsername());
        });
        page.setRecords(records);
        return new PageUtils(page);
    }

}