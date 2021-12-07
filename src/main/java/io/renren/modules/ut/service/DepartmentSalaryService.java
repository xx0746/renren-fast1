package io.renren.modules.ut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.ut.entity.DepartmentSalary;

import java.util.Map;

public interface DepartmentSalaryService extends IService<DepartmentSalary> {
    Map<String, Object> salaryList(Map<String,Object> queryMap);
}
