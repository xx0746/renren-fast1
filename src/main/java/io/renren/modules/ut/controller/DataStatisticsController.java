package io.renren.modules.ut.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysDepartmentEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserRoleEntity;
import io.renren.modules.sys.service.SysRoleService;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.impl.SysDepartmentServiceImpl;
import io.renren.modules.ut.VO.PerformanceVO;
import io.renren.modules.ut.service.DataStatisticsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dataStatistics")
public class DataStatisticsController extends AbstractController {
    @Autowired
    private DataStatisticsService dataStatisticsService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDepartmentServiceImpl sysDepartmentService;
    @Autowired
    private SysRoleService sysRoleService;
    private Logger logger = LoggerFactory.getLogger(DataStatisticsController.class);
    //科研管理
    @Value("${role.researchManagerId}")
    private long researchManagerId;
    //所领导角色id
    @Value("${role.departmentLeaderId}")
    private long departmentLeaderId;
    //院领导角色id
    @Value("${role.companyLeaderId}")
    private long companyLeaderId;

    /**
     * 员工统计列表
     */
    @PostMapping("/userWorkTimeList")
    public R userWorkTimeList(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = dataStatisticsService.userWorkTimeList(params);
        return R.ok(result);
    }

    /**
     * 查询部门列表
     */
    @PostMapping("/queryDepartmentList")
    public R queryDepartmentList() {
        SysUserEntity sysUserEntity = getUser();
        List<SysDepartmentEntity> dataList = new ArrayList<>();
        if(sysUserEntity.getUserId().longValue()!=(long)Constant.SUPER_ADMIN){
            boolean companyLeaderFlag = false;
            boolean departmentLeaderFlag = false;
            boolean researchManagerFlag = false;
            List<SysUserRoleEntity> list = sysUserRoleService.lambdaQuery().eq(SysUserRoleEntity::getUserId, sysUserEntity.getUserId()).list();
            if (CollectionUtil.isNotEmpty(list)) {
                for (SysUserRoleEntity sysUserRoleEntity : list) {
                    long roleId = sysUserRoleEntity.getRoleId();
                    if (companyLeaderId == roleId) {
                        //院领导角色id
                        companyLeaderFlag = true;
                    } else if (departmentLeaderId == roleId) {
                        //所领导角色id
                        departmentLeaderFlag = true;
                    } else if (researchManagerId == roleId) {
                        //科研管理角色id
                        researchManagerFlag = true;
                    }
                }
            }
            if(companyLeaderFlag || researchManagerFlag){
                dataList = sysDepartmentService.lambdaQuery().list();
            }else if(departmentLeaderFlag){
                SysDepartmentEntity sysdepartment = sysDepartmentService.getById(
                        sysUserEntity.getDepartmentId());
                dataList.add(sysdepartment);
            }
        }else {
            dataList = sysDepartmentService.lambdaQuery().list();
        }
        return R.ok().put("dataList",dataList);
    }
    /**
     * 项目统计列表
     */
    @PostMapping("/projectWorkTimeList")
    public R projectWorkTimeList(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = dataStatisticsService.projectWorkTimeList(params);
        return R.ok(result);
    }
    /**
     * 绩效列表
     */
    @PostMapping("/performanceList")
    public R performanceList(@RequestBody Map<String, Object> params){
        Map<String, Object> result = dataStatisticsService.performanceList(params);
        return R.ok(result);
    }
    /**
     * 绩效信息
     */
    @GetMapping("/exportPerformanceList")
    public void exportPerformanceList(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String departmentId = request.getParameter("departmentId");
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(year)){
            params.put("year",year);
        }
        if(StringUtils.isNotBlank(month)){
            params.put("month",month);
        }
        if(StringUtils.isNotBlank(departmentId) && !"null".equals(departmentId)){
            params.put("departmentId",departmentId);
        }

        Map<String, Object> result = dataStatisticsService.performanceList(params);
        List<PerformanceVO> list = (List<PerformanceVO>)result.get("dataList");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("绩效统计", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), PerformanceVO.class).sheet("列表").doWrite(list);


    }
}
