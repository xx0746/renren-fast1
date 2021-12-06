package io.renren.modules.ut.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.ut.entity.DepartmentRatio;
import io.renren.modules.ut.service.DepartmentRatioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department/ratio")
public class DepartmentRatioController extends AbstractController {
    @Autowired
    private DepartmentRatioService departmentRatioService;

    private Logger logger = LoggerFactory.getLogger(DepartmentRatioController.class);
    /**
     * 查询系数列表
     */
    @PostMapping("/ratioList")
    public R ratioList(@RequestBody Map<String, Object> params){
        Map<String,Object> result = departmentRatioService.ratioList(params);
        return R.ok(result);
    }
    /**
     * 更新部门系数
     */
    @PostMapping("/updateRatio")
    public R updateRatio(@RequestBody DepartmentRatio departmentRatio) {
        if(departmentRatio.getId()==null || departmentRatio.getRatio() ==null){
            logger.error("id或系数为空");
            return R.error("id和系数不能为空");
        }
        DepartmentRatio departmentRatio1 = departmentRatioService.getById(departmentRatio.getId());
        departmentRatio1.setRatio(departmentRatio.getRatio());
        departmentRatio1.setModifiedTime(new Date());
        departmentRatioService.updateById(departmentRatio1);
        return R.ok();
    }
    /**
     * 新增部门系数
     */
    @PostMapping("/addRatio")
    public R addRatio(@RequestBody DepartmentRatio departmentRatio) {
        if(departmentRatio.getDepartmentId()==null || departmentRatio.getRatio()==null
                || departmentRatio.getYear()==null || departmentRatio.getMonth()==null){
            return R.error("部门id、系数、年、月不能为空");
        }
        List<DepartmentRatio> departmentRatioList = departmentRatioService.list(
                new QueryWrapper<DepartmentRatio>()
                        .eq("department_id", departmentRatio.getDepartmentId())
                        .eq("year", departmentRatio.getYear())
                        .eq("month", departmentRatio.getMonth())
        );
        if(departmentRatioList.size()>0){
            return R.error("本部门本月已添加了系数，不能重复添加");
        }
        departmentRatio.setCreateTime(new Date());
        departmentRatio.setModifiedTime(new Date());
        departmentRatioService.save(departmentRatio);
        return R.ok();
    }

}
