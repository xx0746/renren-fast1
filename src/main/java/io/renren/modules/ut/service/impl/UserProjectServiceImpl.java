package io.renren.modules.ut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.ut.VO.UserProjectVO;
import io.renren.modules.ut.dao.UserProjectDao;
import io.renren.modules.ut.entity.Project;
import io.renren.modules.ut.entity.UserProject;
import io.renren.modules.ut.service.ProjectService;
import io.renren.modules.ut.service.UserProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userProjectService")
public class UserProjectServiceImpl extends ServiceImpl<UserProjectDao, UserProject> implements UserProjectService {


    @Autowired
    private ProjectService projectService;

    @Override
    public Map<String, Object> personList(Long current, Long size, Long projectId) {
        if (current == null) {
            current = 1l;
        }
        if (size == null) {
            size = Long.valueOf(Integer.MAX_VALUE);
        }
        List<UserProjectVO> userProjectVOs = baseMapper.personList((current-1) * size, size, projectId);
        long total = baseMapper.selectCount(new QueryWrapper<UserProject>().eq("project_id",projectId));
        //Project project = projectService.getById(projectId);
        long usedUt = 0;
        for (UserProjectVO userProjectVO : userProjectVOs) {
            usedUt += userProjectVO.getUt() == null ? 0 : userProjectVO.getUt();
        }
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("msg", "success");
        returnMap.put("current", current);
        returnMap.put("total", total);
        returnMap.put("code", 0);
        returnMap.put("size", userProjectVOs.size());
        returnMap.put("usedUt", usedUt);
        returnMap.put("dataList", userProjectVOs);
        return returnMap;
    }
}
