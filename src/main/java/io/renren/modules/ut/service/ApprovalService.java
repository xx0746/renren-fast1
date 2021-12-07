package io.renren.modules.ut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.ut.entity.UTWrite;

import java.util.Map;

/**
 * <p>
 * 审批 服务类
 * </p>
 *
 * @author java
 * @since 2021-11-27
 */
public interface ApprovalService extends IService<UTWrite> {
    Map<String, Object> managerProjectApprovalList(Map<String,Object> queryMap);
    Map<String, Object> managerApprovalWorkTimeList(Map<String,Object> queryMap);
    Map<String, Object> leaderProjectApprovalList(Map<String,Object> queryMap);
    Map<String, Object> leaderApprovalWorkTimeList(Map<String,Object> queryMap);

}
