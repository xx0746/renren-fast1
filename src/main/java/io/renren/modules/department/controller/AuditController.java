package io.renren.modules.department.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.utils.R;
import io.renren.modules.department.entity.Audit;
import io.renren.modules.department.entity.VO.CommentVO;
import io.renren.modules.department.listener.DesignExcelListener;
import io.renren.modules.department.service.AuditService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserRoleEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/audit")
public class AuditController {
    @Autowired
    private AuditService auditService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    //修改审核状态
    @PostMapping("/updateAudit")
    public R updateAudit(Audit audit) {
        SysUserEntity userEntity = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        List<SysUserRoleEntity> list = sysUserRoleService.lambdaQuery().eq(SysUserRoleEntity::getUserId, userEntity.getUserId()).list();
        List<Long> roleIds = list.stream().map(x -> x.getRoleId()).collect(Collectors.toList());
        if (roleIds.contains(Long.parseLong("7"))) {
            audit.setStatus(audit.getStatus() == 0 ? 1 : 0);
            auditService.update(audit,
                    new QueryWrapper<Audit>()
                            .eq("center_id", audit.getCenterId())
            );
            return R.ok();
        } else {
            return R.error(403, "权限不足");
        }
    }

    //查询审核状态
    @GetMapping("/getStatus")
    public R getStatus(Audit audit) {
        Audit dbAudit = auditService.getOne(
                new QueryWrapper<Audit>()
                        .eq("center_id",audit.getCenterId())
        );
        return R.ok().put("status",dbAudit.getStatus());
    }

}
