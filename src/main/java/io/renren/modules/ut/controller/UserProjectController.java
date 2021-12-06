package io.renren.modules.ut.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.ut.entity.UserProject;
import io.renren.modules.ut.service.UserProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author java
 * @since 2021-11-18
 */
@RestController
@RequestMapping("/userProject")
public class UserProjectController {

    @Autowired
    private UserProjectService userProjectService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询项目成员接口 2.2
     * @param current
     * @param size
     * @param projectId
     * @return
     */
    @PostMapping("/personList")
    public R personList(Long current, Long size, Long projectId) {
        Map<String,Object> returnMap = userProjectService.personList(current, size, projectId);
        return R.ok(returnMap);
    }

    /**
     * 新增项目成员接口 2.3
     * @param ut
     * @param
     * @param projectId
     * @return
     */
    @PostMapping("/addPerson")
    public R addPerson(Double ut, Long userId, Long projectId) {

        List<UserProject> list = userProjectService.list(
                new QueryWrapper<UserProject>()
                        .eq("project_id", projectId)
                        .eq("user_id", userId));
        if(list.size()>0){
            return R.error("该项目已分配给该员工，不能重复添加");
        }
        UserProject userProject = new UserProject(projectId,userId,ut);
        userProjectService.save(userProject);
        return R.ok();
    }

    /**
     * 删除项目成员接口  2.4
     * @param personId
     * @param projectId
     * @return
     */
    @PostMapping("/deletePerson")
    public R deletePerson(Long personId, Long projectId) {
        boolean result = userProjectService.remove(
                new QueryWrapper<UserProject>()
                .eq("user_id", personId)
                .eq("project_id", projectId)
        );
        return R.ok();
    }


}

