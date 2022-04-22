package io.renren.modules.ut.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.ut.entity.UTWrite;
import io.renren.modules.ut.entity.UTWriteDay;
import io.renren.modules.ut.service.ApprovalService;
import io.renren.modules.ut.service.WriteDayService;
import io.renren.modules.ut.service.WriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/approval")
public class ApprovalController extends AbstractController {
    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private WriteService writeService;
    @Autowired
    private WriteDayService writeDayService;

    private Logger logger = LoggerFactory.getLogger(ApprovalController.class);
    /**
     * 项目经理项目列表（周工时）
     */
    @PostMapping("/managerProjectApprovalList")
    public R managerProjectApprovalList(@RequestBody Map<String, Object> params){
        SysUserEntity user = getUser();
        if(user.getUserId()!=(long) Constant.SUPER_ADMIN){
            params.put("userId",user.getUserId());
        }
        Map<String,Object> result = approvalService.managerProjectApprovalList(params);
        return R.ok(result);
    }

    /**
     * 项目经理项目列表（日工时）
     */
    @PostMapping("/managerProjectDayApprovalList")
    public R managerProjectDayApprovalList(@RequestBody Map<String, Object> params){
        SysUserEntity user = getUser();
        if(user.getUserId()!=(long) Constant.SUPER_ADMIN){
            params.put("userId",user.getUserId());
        }
        Map<String,Object> result = approvalService.managerProjectDayApprovalList(params);
        return R.ok(result);
    }

    /**
     * 项目经理周工作类型工时列表
     */
    @PostMapping("/managerApprovalWorkTimeList")
    public R managerApprovalWorkTimeList(@RequestBody Map<String, Object> params){
        Map<String,Object> result = approvalService.managerApprovalWorkTimeList(params);
        return R.ok(result);
    }

    /**
     * 项目经理日工作类型工时列表
     */
    @PostMapping("/managerDayApprovalWorkTimeList")
    public R managerDayApprovalWorkTimeList(@RequestBody Map<String, Object> params){
        Map<String,Object> result = approvalService.managerDayApprovalWorkTimeList(params);
        return R.ok(result);
    }
    /**
     * 项目经理周工作类型工时审批
     */
    @PostMapping("/managerApprovalWorkTime")
    public R managerApprovalWorkTime(@RequestBody Map<String, Object> params){
        List<Map> dataList = (List<Map>)params.get("dataList");
        if(dataList==null || dataList.size()==0){
            return R.error("请刷新页面后重试");
        }
        List<UTWrite> writeList = new ArrayList<>();
        for(Map map:dataList){
            List<Map> workTypelist = (List<Map>)map.get("workTypelist");
            if(workTypelist==null || workTypelist.size()==0){
                continue;
            }
            for(Map work : workTypelist){
                String idStr = String.valueOf(work.get("id"));
                UTWrite write =  writeService.getById(Long.parseLong(idStr));
                write.setUt((Double.parseDouble(String.valueOf(work.get("ut")))));
                write.setModifiedTime(new Date());
                write.setStatus(1);
                writeList.add(write);
            }
        }
        if(writeList.size()>0){
            writeService.updateBatchById(writeList);
        }
        return R.ok();
    }

    /**
     * 项目经理日工作类型工时审批
     */
    @PostMapping("/managerDayApprovalWorkTime")
    public R managerDayApprovalWorkTime(@RequestBody Map<String, Object> params){
        List<Map> dataList = (List<Map>)params.get("dataList");
        if(dataList==null || dataList.size()==0){
            return R.error("请刷新页面后重试");
        }
        List<UTWriteDay> writeDayList = new ArrayList<>();
        for(Map map:dataList){
            List<Map> workTypelist = (List<Map>)map.get("workTypelist");
            if(workTypelist==null || workTypelist.size()==0){
                continue;
            }
            for(Map work : workTypelist){
                String idStr = String.valueOf(work.get("id"));
                UTWriteDay writeDay =  writeDayService.getById(Long.parseLong(idStr));
                writeDay.setUt((Double.parseDouble(String.valueOf(work.get("ut")))));
                writeDay.setModifiedTime(new Date());
                writeDay.setStatus(1);
                writeDayList.add(writeDay);
            }
        }
        if(writeDayList.size()>0){
            writeDayService.updateBatchById(writeDayList);
        }
        return R.ok();
    }
    /**
     * 领导项目列表（月工时）
     */
    @PostMapping("/leaderProjectApprovalList")
    public R leaderProjectApprovalList(@RequestBody Map<String, Object> params){
        SysUserEntity user = getUser();
        if(user.getUserId()!=(long) Constant.SUPER_ADMIN){
            params.put("departmentId",user.getDepartmentId());
        }
        Map<String,Object> result = approvalService.leaderProjectApprovalList(params);
        return R.ok(result);
    }
    /**
     * 领导 月总工时列表
     */
    @PostMapping("/leaderApprovalWorkTimeList")
    public R leaderApprovalWorkTimeList(@RequestBody Map<String, Object> params){
        Map<String,Object> result = approvalService.leaderApprovalWorkTimeList(params);
        return R.ok(result);
    }
    /**
     * 领导 审批
     */
    @PostMapping("/leaderApprovalWorkTime")
    public R leaderApprovalWorkTime(@RequestBody Map<String, Object> params){
        if(params.get("yearmonth") ==null || params.get("projectId")==null){
            return R.error("审批错误，请联系管理员");
        }
        QueryWrapper<UTWrite> utWriteQueryWrapper = new QueryWrapper<>();
        utWriteQueryWrapper.eq("yearmonth", params.get("yearmonth"));
        utWriteQueryWrapper.eq("status", 1);
        utWriteQueryWrapper.eq("project_id", params.get("projectId"));
        List<UTWrite> list = approvalService.list(utWriteQueryWrapper);
        for(UTWrite write : list){
            write.setStatus(2);
            write.setModifiedTime(new Date());
        }
        if(list.size()>0){
            approvalService.updateBatchById(list);
        }
        return R.ok();
    }
}
