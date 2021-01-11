package io.renren.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import io.renren.modules.performance.entity.PerformanceEntity;
import io.renren.modules.performance.entity.PerformanceFunctionEntity;
import io.renren.modules.performance.service.PerformanceFunctionService;
import io.renren.modules.performance.service.PerformanceService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class PerformanceFunctionListener extends AnalysisEventListener<PerformanceFunctionEntity> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    /**
     * 这个集合用于接收 读取Excel文件得到的数据
     */
    List<PerformanceFunctionEntity> list = new ArrayList<PerformanceFunctionEntity>();

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private PerformanceFunctionService performanceFunctionService;
    private SysUserService userService;

    public PerformanceFunctionListener() {

    }

    /**
     *
     * 不要使用自动装配
     * 在测试类中将dao当参数传进来
     */
    public PerformanceFunctionListener(PerformanceFunctionService performanceFunctionService, SysUserService userService) {
        this.performanceFunctionService = performanceFunctionService;
        this.userService = userService;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     */
    @Override
    public void invoke(PerformanceFunctionEntity entity, AnalysisContext context) {

        list.add(entity);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();

    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        for (PerformanceFunctionEntity entity : list) {
            if (entity!=null) {
                SysUserEntity one = userService.queryByUserName(entity.getUserName());
                if (one == null) {
                    break;
                }
                PerformanceFunctionEntity one1 = performanceFunctionService.lambdaQuery().eq(PerformanceFunctionEntity::getUserId, one.getUserId()).one();
                if (one1 != null) {
                    break;
                }
                if (CollectionUtil.isEmpty(one.getRoleIdList())) {
                    break;
                }
                Long aLong = one.getRoleIdList().get(0);
                if (aLong != Long.parseLong("10")) {
                    break;
                }
//                this.dataForm.workload = data.performanceFunction.workload
//                this.dataForm.duty = data.performanceFunction.duty
//                this.dataForm.discipline = data.performanceFunction.discipline
//                this.dataForm.attitude = data.performanceFunction.attitude
//                this.dataForm.scorecount = data.performanceFunction.scorecount
//                this.dataForm.deduction = data.performanceFunction.deduction
                entity.setCreateTime(new Date());
                entity.setStatus(1);
                entity.setUserId(one.getUserId());
                entity.setDuty(entity.getDuty());
                entity.setWorkload(entity.getWorkload());
                entity.setDiscipline(entity.getDiscipline());
                entity.setAttitude(entity.getAttitude());
                entity.setDeduction(entity.getDeduction());
                entity.setScorecount(entity.getDuty()+entity.getWorkload()+entity.getDiscipline()+entity.getAttitude()-entity.getDeduction());
                performanceFunctionService.save(entity);
            } else {
                break;
            }
        }
    }
    public static boolean objecIsNull(SysUserEntity sysUserEntity){
        if (sysUserEntity == null) {
            return true;
        }
        if (StrUtil.isEmpty(sysUserEntity.getUsername())) {
            return true;
        }
        if (StrUtil.isEmpty(sysUserEntity.getMobile())) {
            return true;
        }
        if (StrUtil.isEmpty(sysUserEntity.getEmail())) {
            return true;
        }
        if (StrUtil.isEmpty(sysUserEntity.getStaff())) {
            return true;
        }
        if (StrUtil.isEmpty(sysUserEntity.getLevel())) {
            return true;
        }
        return false;
    }
}
