package io.renren.modules.ut.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.ut.entity.DepartmentSalary;
import io.renren.modules.ut.service.DepartmentSalaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departmentSalary")
public class DepartmentSalaryController extends AbstractController {
    @Autowired
    private DepartmentSalaryService departmentSalaryService;

    private Logger logger = LoggerFactory.getLogger(DepartmentSalaryController.class);
    /**
     * 查询工资总额列表
     */
    @PostMapping("/salaryList")
    public R salaryList(@RequestBody Map<String, Object> params){
        Map<String,Object> result = departmentSalaryService.salaryList(params);
        return R.ok(result);
    }
    /**
     * 更新部门工资总额
     */
    @PostMapping("/updateSalary")
    public R updateSalary(@RequestBody DepartmentSalary departmentSalary) {
        if(departmentSalary.getId()==null || departmentSalary.getSalary() ==null){
            logger.error("id或工资总额为空");
            return R.error("部门和工资总额不能为空");
        }
        DepartmentSalary departmentSalary1 = departmentSalaryService.getById(departmentSalary.getId());
        departmentSalary1.setSalary(departmentSalary.getSalary());
        departmentSalary1.setModifiedTime(new Date());
        departmentSalaryService.updateById(departmentSalary1);
        return R.ok();
    }
    /**
     * 新增部门工资总额
     */
    @PostMapping("/addSalary")
    public R addSalary(@RequestBody DepartmentSalary departmentSalary) {
        if(departmentSalary.getDepartmentId()==null || departmentSalary.getSalary()==null
                || departmentSalary.getYear()==null || departmentSalary.getMonth()==null){
            return R.error("部门、工资总额、年、月不能为空");
        }
        List<DepartmentSalary> departmentSalaryList = departmentSalaryService.list(
                new QueryWrapper<DepartmentSalary>()
                        .eq("department_id", departmentSalary.getDepartmentId())
                        .eq("year", departmentSalary.getYear())
                        .eq("month", departmentSalary.getMonth())
        );
        if(departmentSalaryList.size()>0){
            return R.error("本部门本月已添加了工资总额，不能重复添加");
        }
        departmentSalary.setCreateTime(new Date());
        departmentSalary.setModifiedTime(new Date());
        departmentSalary.setYearmonth(Integer.parseInt(departmentSalary.getYear()+departmentSalary.getMonth()));
        departmentSalaryService.save(departmentSalary);
        return R.ok();
    }

}
