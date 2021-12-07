package io.renren.modules.ut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.ut.VO.AgvSalaryVO;
import io.renren.modules.ut.VO.UserSalaryDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DataStatisticsService extends IService<Map> {
    Map<String, Object> userWorkTimeList(Map<String,Object> queryMap);
    Map<String, Object> projectWorkTimeList(Map<String,Object> queryMap);
    Map<String, Object> performanceList(Map<String,Object> queryMap);

    List<AgvSalaryVO> getAgvSalary(Map<String,Object> queryMap);
    List<UserSalaryDetailVO> getUserSalaryDetail(Map<String,Object> queryMap);

}
