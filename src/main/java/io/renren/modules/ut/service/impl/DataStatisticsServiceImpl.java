package io.renren.modules.ut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.ut.VO.AgvSalaryVO;
import io.renren.modules.ut.VO.PerformanceVO;
import io.renren.modules.ut.VO.UserSalaryDetailVO;
import io.renren.modules.ut.dao.DataStatisticsDao;
import io.renren.modules.ut.entity.DepartmentSalary;
import io.renren.modules.ut.entity.UTWrite;
import io.renren.modules.ut.entity.UserScore;
import io.renren.modules.ut.service.DataStatisticsService;
import io.renren.modules.ut.service.DepartmentSalaryService;
import io.renren.modules.ut.service.UserScoreService;
import io.renren.modules.ut.service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("DataStatisticsService")
public class DataStatisticsServiceImpl extends ServiceImpl<DataStatisticsDao, Map> implements DataStatisticsService {
    @Autowired
    private DepartmentSalaryService departmentSalaryService;
    @Autowired
    private UserScoreService userScoreService;
    @Autowired
    private WriteService writeService;
    @Override
    public Map<String, Object> userWorkTimeList(Map<String, Object> queryMap) {
        int current = (int) queryMap.get("current");
        int size = (int) queryMap.get("size");
        queryMap.put("start", (current - 1) * size);
        queryMap.put("size", size);
        List<Map> list = baseMapper.userWorkTimeList(queryMap);
        long total = baseMapper.userWorkTimeCount(queryMap);
        Map<String, Object> map = new HashMap<>();
        map.put("current", queryMap.get("current"));
        map.put("size", queryMap.get("size"));
        map.put("total", total);
        map.put("dataList", list);
        return map;
    }
    @Override
    public Map<String, Object> projectWorkTimeList(Map<String, Object> queryMap) {
        int current = (int) queryMap.get("current");
        int size = (int) queryMap.get("size");
        queryMap.put("start", (current - 1) * size);
        queryMap.put("size", size);
        queryMap.put("yearMonth",Long.parseLong(queryMap.get("year")+(String)queryMap.get("month")));
        List<Map> list = baseMapper.projectWorkTimeList(queryMap);
        long total = baseMapper.projectWorkTimeCount(queryMap);
        Map<String, Object> map = new HashMap<>();
        map.put("current", queryMap.get("current"));
        map.put("size", queryMap.get("size"));
        map.put("total", total);
        map.put("dataList", list);
        return map;
    }
    @Override
    public Map<String, Object> performanceList(Map<String, Object> queryMap) {
        long total = 0;
        if(queryMap.get("current") !=null && queryMap.get("size") !=null){
            int current = (int) queryMap.get("current");
            int size = (int) queryMap.get("size");
            queryMap.put("start", (current - 1) * size);
            queryMap.put("size", size);
            total = baseMapper.performanceCount(queryMap);
        }
        List<PerformanceVO> list = baseMapper.performanceList(queryMap);
        if(total ==0){
            total = list.size();
        }

        String errStr = "";
        if(list.size()==0){
            errStr = "原因：";
            Integer departmentId = (Integer) queryMap.get("departmentId");
            String year  = (String)queryMap.get("year");
            String month = (String)queryMap.get("month");
            List<DepartmentSalary> departmentSalaries = departmentSalaryService.list(
                    new QueryWrapper<DepartmentSalary>()
                            .eq("yearmonth", year+month)
                            .eq("department_id", departmentId)
            );
            if(departmentSalaries.size()==0){
                errStr+="【没有设置部门预算总工资】";
            }
            int userScoreCount = userScoreService.count(
                    new QueryWrapper<UserScore>()
                            .eq("year", year)
                            .eq("month",month)
                            .eq("department_id", departmentId)
            );
            if (userScoreCount == 0) {
                errStr+="【领导没有给员工打分】";
            }
            int utWriteCount = writeService.count(
                    new QueryWrapper<UTWrite>()
                            .eq("yearmonth", Integer.parseInt(year+month))
                            .eq("status", 2)
                            .eq("department_id", departmentId)
            );
            if (utWriteCount == 0) {
                errStr+="【员工工时还没审批】";
            }

        }
        Map<String, Object> map = new HashMap<>();
        map.put("errStr",errStr);
        map.put("current", queryMap.get("current"));
        map.put("size", queryMap.get("size"));
        map.put("total", total);
        map.put("dataList", list);
        return map;
    }
    @Override
    public List<AgvSalaryVO> getAgvSalary(Map<String,Object> queryMap) {
    return baseMapper.getAgvSalary(queryMap);
    }
    @Override
    public List<UserSalaryDetailVO> getUserSalaryDetail(Map<String,Object> queryMap) {
        return baseMapper.getUserSalaryDetail(queryMap);
    }

}
