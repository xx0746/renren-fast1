package io.renren.modules.ut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.ut.dao.ApprovalDao;
import io.renren.modules.ut.entity.UTWrite;
import io.renren.modules.ut.service.ApprovalService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 审批 服务实现类
 * </p>
 *
 * @author java
 * @since 2021-11-27
 */
@Service("ApprovalService")
public class ApprovalServiceImpl extends ServiceImpl<ApprovalDao, UTWrite> implements ApprovalService {
    @Override
    public Map<String, Object> managerProjectApprovalList(Map<String, Object> queryMap) {
        int current = (int) queryMap.get("current");
        int size = (int) queryMap.get("size");
        queryMap.put("start", (current - 1) * size);
        queryMap.put("size", size);
        queryMap.put("yearMonthWeek",Long.parseLong((String)queryMap.get("yearMonthWeek")));
        List<Map> list = baseMapper.managerProjectApprovalList(queryMap);
        long total = baseMapper.projectApprovalCount(queryMap);
        Map<String, Object> map = new HashMap<>();
        map.put("current", queryMap.get("current"));
        map.put("size", queryMap.get("size"));
        map.put("total", total);
        map.put("dataList", list);
        return map;
    }
    @Override
    public Map<String, Object> managerApprovalWorkTimeList(Map<String, Object> queryMap) {
        queryMap.put("yearMonthWeek",Long.parseLong((String)queryMap.get("yearMonthWeek")));
        if("1".equals(String.valueOf(queryMap.get("queryType")))){
            queryMap.put("status","1,2");
        }else if("2".equals(String.valueOf(queryMap.get("queryType")))){
            queryMap.put("status","0");
        }else {
            return new HashMap<>();
        }
        List<Map> list = baseMapper.managerApprovalWorkTimeList(queryMap);
        Map<String,Object> result = new HashMap();
        for(Map map : list){
            String userId = String.valueOf(map.get("userId"));
            String username = (String)map.get("username");
            List<Map> maps = (List<Map>)result.get(userId+":"+username);
            Double weekTime = (Double)result.get(userId+"weekTime");
            if(maps ==null){
                maps = new ArrayList<>();
            }
            if(weekTime == null){
                weekTime = 0.0;
            }
            Double ut = (Double)map.get("ut");
            if(ut == null){
                ut=0.0;
            }
            maps.add(map);
            result.put(userId+":"+username,maps);
            result.put(userId+"weekTime",weekTime+ut);
        }
        List<Map> resultList = new ArrayList<>();
        for(String key:result.keySet()){
            if(key.contains("weekTime")){
                continue;
            }
            String[] keys = key.split(":");
            List<Map> workTypelist = (List<Map>)result.get(key);
            Map map = new HashMap();
            map.put("workTypelist",workTypelist);
            map.put("userId",keys[0]);
            map.put("username",keys[1]);
            map.put("weekTime",result.get(keys[0]+"weekTime"));
            resultList.add(map);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dataList", resultList);
        return map;
    }
    @Override
    public Map<String, Object> leaderProjectApprovalList(Map<String, Object> queryMap) {
        int current = (int) queryMap.get("current");
        int size = (int) queryMap.get("size");
        queryMap.put("start", (current - 1) * size);
        queryMap.put("size", size);
        queryMap.put("yearMonth",Long.parseLong((String)queryMap.get("yearMonth")));
        List<Map> list = baseMapper.leaderProjectApprovalList(queryMap);
        long total = baseMapper.projectApprovalCount(queryMap);
        Map<String, Object> map = new HashMap<>();
        map.put("current", queryMap.get("current"));
        map.put("size", queryMap.get("size"));
        map.put("total", total);
        map.put("dataList", list);
        return map;
    }
    @Override
    public Map<String, Object> leaderApprovalWorkTimeList(Map<String, Object> queryMap) {
        queryMap.put("yearMonth",Long.parseLong((String)queryMap.get("yearMonth")));
        if("1".equals(String.valueOf(queryMap.get("queryType")))){
            queryMap.put("status",1);
        }else if("2".equals(String.valueOf(queryMap.get("queryType")))){
            queryMap.put("status",2);
        }else {
            return new HashMap<>();
        }
        List<Map> list = baseMapper.leaderApprovalWorkTimeList(queryMap);
        Map<String, Object> map = new HashMap<>();
        map.put("dataList", list);
        return map;
    }
}
