package io.renren.modules.department.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.utils.R;
import io.renren.modules.department.entity.Audit;
import io.renren.modules.department.entity.VO.CommentVO;
import io.renren.modules.department.listener.DesignExcelListener;
import io.renren.modules.department.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/audit")
public class AuditController {
    @Autowired
    private AuditService auditService;

    //修改审核状态
    @PostMapping("/updateAudit")
    public R updateAudit(Audit audit) {
        audit.setStatus(audit.getStatus() == 0 ? 1 : 0);
        auditService.update(audit,
                new QueryWrapper<Audit>()
                        .eq("center_id",audit.getCenterId())
        );
        return R.ok();
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
