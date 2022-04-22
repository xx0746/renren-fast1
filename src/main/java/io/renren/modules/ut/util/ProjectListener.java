package io.renren.modules.ut.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.druid.sql.dialect.h2.visitor.H2ASTVisitor;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysDepartmentEntity;
import io.renren.modules.sys.entity.SysRoleEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDepartmentService;
import io.renren.modules.sys.service.SysRoleService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.ut.entity.Project;
import io.renren.modules.ut.entity.UserProject;
import io.renren.modules.ut.service.ProjectService;
import io.renren.modules.ut.service.UserProjectService;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class ProjectListener extends AnalysisEventListener<Project> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 10;

    /**
     * 这个集合用于接收 读取Excel文件得到的数据
     */
    List<Project> list = new ArrayList<Project>();

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private ProjectService projectService;
    private UserProjectService userProjectService;
    private String excelMonth;
    private SysUserService sysUserService;
    SysDepartmentService sysDepartmentService;

    public ProjectListener() {

    }

    /**
     * 不要使用自动装配
     * 在测试类中将dao当参数传进来
     */
    public ProjectListener(ProjectService projectService, UserProjectService userProjectService, SysUserService sysUserService,
                           SysDepartmentService sysDepartmentService, String excelMonth) {
        this.projectService = projectService;
        this.excelMonth = excelMonth;
        this.userProjectService = userProjectService;
        this.sysUserService = sysUserService;
        this.sysDepartmentService = sysDepartmentService;
    }

    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(Project project, AnalysisContext context) {
        project.setExcelMonth(this.excelMonth);
        String userName = project.getUserName().trim().replaceAll(" ","")
                .replaceAll("  ","");
        if (StringUtils.isNotBlank(userName)) {
            if (userName.contains("\n")) {
                String[] userNames = userName.split("\n");
                userName = userNames[0];
            } else if (userName.contains(",")) {
                String[] userNames = userName.split(",");
                userName = userNames[0];
            } else if (userName.contains("，")) {
                String[] userNames = userName.split("，");
                userName = userNames[0];
            }
            Map<String, Object> params = new HashMap<>();
            params.put("username", userName);
            PageUtils page = sysUserService.queryPage(params);
            if (page.getList().size() > 0) {
                List<SysUserEntity> userList = (List<SysUserEntity>) page.getList();
                project.setUserId(userList.get(0).getUserId());
            }
            project.setUserName(userName);
        }

        String departmentName = project.getDepartmentName().trim()
                .replaceAll("（牵头）","").replaceAll("(牵头)","");
        if (StringUtils.isNotBlank(departmentName)) {
            if (departmentName.contains("\n")) {
                String[] departmentNames = departmentName.split("\n");
                departmentName = departmentNames[0];
            } else if (userName.contains(",")) {
                String[] departmentNames = departmentName.split(",");
                departmentName = departmentNames[0];
            } else if (userName.contains("，")) {
                String[] departmentNames = departmentName.split("，");
                departmentName = departmentNames[0];
            }
            Map<String,Object> params = new HashMap<>();
            params.put("departmentName",departmentName);
            Map<String,Object> result = sysDepartmentService.getDepartmentByMap(params);
            List<SysDepartmentEntity> departList = (List<SysDepartmentEntity>)result.get("dataList");
            if(departList!=null && departList.size()>0){
                project.setDepartmentId(departList.get(0).getId());
            }
            project.setDepartmentName(departmentName);
        }
        project.setCreateTime(new Date());
        project.setModifiedTime(new Date());
        list.add(project);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();

    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        for (Project project : list) {
            if (!objecIsNull(project)) {
               /* sysUserEntity.setPassword(sysUserEntity.getMobile());
                sysUserEntity.setStatus(1);
                sysUserService.saveUser(sysUserEntity);*/
                //sysUserService.queryByUserName()
                projectService.save(project);
                if(project.getProjectUserNames() != null && project.getProjectUserNames().length() > 0){
                    String[] userNames = project.getProjectUserNames().split(",");
                    for (String userName : userNames) {
                        userName = userName.trim().replaceAll(" ","")
                                .replaceAll("  ","");
                        if(userName != null && userName.length() > 0){
                            SysUserEntity sysUserEntity = sysUserService.selectByUserName(userName);
                            if(sysUserEntity != null){
                                UserProject userProject = new UserProject(project.getId(),sysUserEntity.getUserId(), 0D);
                                userProjectService.save(userProject);
                            }
                        }
                    }
                }
            } else {
                break;
            }
        }
    }

    public static boolean objecIsNull(Project project) {
        /*if (sysUserEntity == null) {
            return true;
        }
        if (StrUtil.isEmpty(sysUserEntity.getUsername())) {
            return true;
        }
        if (StrUtil.isEmpty(sysUserEntity.getMobile())) {
            return true;
        }
        if (StrUtil.isEmpty(sysUserEntity.getEmail())) {
            return true;
        }
        if (StrUtil.isEmpty(sysUserEntity.getStaff())) {
            return true;
        }
        if (StrUtil.isEmpty(sysUserEntity.getLevel())) {
            return true;
        }*/
        return false;
    }
}
