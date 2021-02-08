package io.renren.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import io.renren.modules.performance.entity.PerformanceEntity;
import io.renren.modules.performance.service.PerformanceService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class PerformanceListener extends AnalysisEventListener<PerformanceEntity> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    /**
     * 这个集合用于接收 读取Excel文件得到的数据
     */
    List<PerformanceEntity> list = new ArrayList<PerformanceEntity>();

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private PerformanceService performanceService;
    private SysUserService userService;

    private SysUserEntity currentUser;

    public PerformanceListener() {

    }

    /**
     *
     * 不要使用自动装配
     * 在测试类中将dao当参数传进来
     */
    public PerformanceListener(PerformanceService performanceService,SysUserService userService,SysUserEntity userEntity) {
        this.performanceService = performanceService;
        this.userService = userService;
        this.currentUser = userEntity;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     */
    @Override
    public void invoke(PerformanceEntity entity, AnalysisContext context) {

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
        for (PerformanceEntity entity : list) {
            if (entity!=null) {
                SysUserEntity one = userService.queryByUserName(entity.getUserName());
                if (one == null) {
                    break;
                }
                PerformanceEntity one1 = performanceService.lambdaQuery().eq(PerformanceEntity::getUserId, one.getUserId()).one();
                if (one1 != null) {
                    break;
                }
                if (CollectionUtil.isEmpty(one.getRoleIdList())) {
                    break;
                }
                Long aLong = one.getRoleIdList().get(0);

                if (aLong == Long.parseLong("12")) {
                    entity.setAdvanced(null);
                }
                if (aLong == Long.parseLong("14")) {
                    entity.setAdvanced(null);
                    entity.setResearch(null);
                }
                if (aLong == Long.parseLong("16")) {
                    entity.setAdvanced(null);
                }
                if (aLong == Long.parseLong("18")) {
                    entity.setAdvanced(null);
                    entity.setResearch(null);
                    entity.setProcess(null);
                    entity.setAchievement(null);
                    entity.setEvaluation(null);
                } else {
                    entity.setEvaluation((entity.getAdvanced()==null? 0:entity.getAdvanced())+ (entity.getResearch()==null ? 0:entity.getResearch())+(entity.getProcess()==null ? 0:entity.getResearch())+(entity.getAchievement()==null ? 0:entity.getResearch()));
                }
                entity.setCreateTime(new Date());
                entity.setStatus(1);
                entity.setUserId(one.getUserId());
                entity.setCreateUserId(currentUser.getUserId());
                entity.setBehavior(entity.getKnowledge()+entity.getCooperation()+entity.getLearning()+entity.getResponsibility()+entity.getDiscipline());
                entity.setExamine(entity.getWorkload()+entity.getEvaluation()+entity.getBehavior());
                performanceService.save(entity);
            } else {
                break;
            }
        }
        performanceService.autorange();
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
