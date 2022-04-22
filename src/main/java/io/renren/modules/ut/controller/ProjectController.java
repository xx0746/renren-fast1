package io.renren.modules.ut.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.log.Log;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.common.utils.SysUserListener;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDepartmentService;
import io.renren.modules.sys.service.SysRoleService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.ut.entity.Project;
import io.renren.modules.ut.service.ProjectService;
import io.renren.modules.ut.service.UserProjectService;
import io.renren.modules.ut.service.WriteService;
import io.renren.modules.ut.util.ProjectListener;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
public class ProjectController extends AbstractController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserProjectService userProjectService;
    @Autowired
    private WriteService writeService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDepartmentService sysDepartmentService;
    private Logger logger = LoggerFactory.getLogger(ProjectController.class);
    /**
     * 查询项目列表
     */
    @PostMapping("/projectList")
    public R list(@RequestBody Map<String, Object> params){
        String queryType = (String)params.get("queryType");
        if("1".equals(queryType)){
            //查询自己负责的项目
            long userId = getUserId();
            if(userId!=(long) Constant.SUPER_ADMIN){
                params.put("userId",userId);
            }
        }
        Map<String,Object> result = projectService.getProjectByMap(params);
        return R.ok(result);
    }
    /**
     * 更新项目总工时
     */
    @PostMapping("/updateProject")
    public R updateProject(@RequestBody Project project) {
        if(project.getId()==null || project.getUt()==0){
            logger.error("项目id和项目工时为空");
            return R.error("项目id和项目工时不能为空");
        }
        Project projectOld = projectService.getById(project.getId());
        projectOld.setUt(project.getUt());
        projectOld.setModifiedTime(new Date());
        projectService.updateById(projectOld);
        return R.ok();
    }
    /**
     * 上传项目
     */
    @PostMapping("/projectUpload")
    public R projectUpload(@RequestParam("file") MultipartFile file,@RequestParam("excelMonth") String excelMonth) {
        try {
            EasyExcel.read(file.getInputStream(), Project.class,
                    new ProjectListener(projectService,userProjectService,sysUserService,sysDepartmentService,excelMonth))
                    .sheet().headRowNumber(2).doRead();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Excel解析出错");
            return R.error("导入模板文件不正确，请修改后，重新上传");
        }
        return R.ok();
    }


}
