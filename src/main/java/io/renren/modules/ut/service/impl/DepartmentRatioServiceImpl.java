package io.renren.modules.ut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.ut.dao.DepartmentRatioDao;
import io.renren.modules.ut.entity.DepartmentRatio;
import io.renren.modules.ut.service.DepartmentRatioService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("DepartmentRatioService")
public class DepartmentRatioServiceImpl extends ServiceImpl<DepartmentRatioDao, DepartmentRatio> implements DepartmentRatioService {
    @Override
    public Map<String, Object> ratioList(Map<String,Object> queryMap) {
        int total =0;
        if(queryMap.get("current") !=null && queryMap.get("size") !=null){
            int current =(int)queryMap.get("current");
            int size =(int)queryMap.get("size");
            queryMap.put("start",(current-1)*size);
            queryMap.put("size",size);
            QueryWrapper<DepartmentRatio> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("modified_time");
            if(queryMap.get("departmentId") !=null && !queryMap.get("departmentId").toString().equals("")){
                wrapper.eq("department_id",queryMap.get("departmentId"));
            }
            if(queryMap.get("year") !=null ){
                wrapper.eq("year",queryMap.get("year"));
            }
            if(queryMap.get("month") !=null){
                wrapper.eq("month",queryMap.get("month"));
            }
            total = baseMapper.selectCount(wrapper);
        }

        List<Map> list = baseMapper.ratioList(queryMap);
        if(total==0){
            total = list.size();
        }

        Map<String,Object> map = new HashMap<>();
        map.put("current",queryMap.get("current"));
        map.put("size",queryMap.get("size"));
        map.put("total",total);
        map.put("dataList",list);
        return map;
    }
}
