package io.renren.modules.ut.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.ut.VO.WriteDayVO;
import io.renren.modules.ut.entity.UTWriteDay;
import io.renren.modules.ut.service.WorkTypeService;
import io.renren.modules.ut.service.WriteDayService;
import io.renren.modules.ut.service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * ut填报表 前端控制器
 * </p>
 *
 * @author java
 * @since 2021-11-27
 */
@RestController
@RequestMapping("/utWriteDay")
public class WriteDayController extends AbstractController {
    @Autowired
    private WriteDayService writeDayService; //UT填报的service

    @Autowired
    private WorkTypeService workTypeService;

    @Autowired
    private SysUserService sysUserService;
    /**
     * 3.1
     * @param current
     * @param size
     * @return
     */
    @PostMapping("/writeProjectList")
    public R writeProjectList(@RequestParam("current") Long current, @RequestParam("size") Long size) {
        SysUserEntity user = getUser();
        Map<String, Object> returnMap = writeDayService.writeProjectList(user, current, size);
        return R.ok(returnMap);
    }
    /**
     * 3.2
     */
    @PostMapping("/writeList")
    public R writeList(@RequestParam("current") Long current, @RequestParam("size") Long size, @RequestParam("year") String year,
                       @RequestParam("month") String month, @RequestParam("day") String day, @RequestParam("projectId") Long projectId) {
        Long userId = getUserId();
        Map<String, Object> returnMap = writeDayService.writeList(userId, current, size, year, month, day, projectId);
        return R.ok(returnMap);
    }
    /**
     * 3.4
     */
    @PostMapping("/writeAdd")
    public R writeAdd(@RequestBody WriteDayVO writeVO) {
        QueryWrapper<UTWriteDay> utWriteDayQueryWrapper = new QueryWrapper<>();
        utWriteDayQueryWrapper.eq(writeVO.getYear() != null, "year", writeVO.getYear());
        utWriteDayQueryWrapper.eq(writeVO.getMonth() != null, "month", writeVO.getMonth());
        utWriteDayQueryWrapper.eq(writeVO.getDay() != null, "day", writeVO.getDay());
        utWriteDayQueryWrapper.eq(writeVO.getProjectId() != null, "project_id", writeVO.getProjectId());
        utWriteDayQueryWrapper.eq("user_id", getUserId());
        List<UTWriteDay> list = writeDayService.list(utWriteDayQueryWrapper);
        List<Long> ids = new ArrayList<>();
        for(UTWriteDay write :list){
            int status = write.getStatus();
            ids.add(write.getId());
            if(status !=0){
                return R.error("该周工时已经在审批中，无法操作");
            }
        }
        if(list.size()>0){
            writeDayService.removeByIds(ids);
        }
        List<UTWriteDay> writes = writeVO.getDataList().stream().map(entry -> {
            UTWriteDay write = new UTWriteDay();
            SysUserEntity user = getUser();
            write.setUserId(user.getUserId());
            write.setDepartmentId(user.getDepartmentId());
            write.setProjectId(writeVO.getProjectId());
            write.setYear(writeVO.getYear());
            write.setMonth(writeVO.getMonth());
            write.setDay(writeVO.getDay());
            write.setYearmonth(Long.parseLong(writeVO.getYear()+writeVO.getMonth()));
            write.setYearMonthDay(Long.parseLong(writeVO.getYear()+writeVO.getMonth()+writeVO.getDay()));
            write.setOneId(entry.getOneId());
            write.setTwoId(entry.getTwoId());
            write.setUt(entry.getUt());
            write.setCreateTime(new Date());
            write.setModifiedTime(new Date());
            //Write write = new Write(writeVO.getYear(), writeVO.getMonth(), writeVO.getWeek(), writeVO.getProjectId(), entry.getOneId(), entry.getTwoId(), getUserId(), entry.getUt());
            return write;
        }).collect(Collectors.toList());
        if(writes.size()>0){
            writeDayService.saveBatch(writes);
        }
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("code", 0);
        returnMap.put("msg", "success");
        return R.ok(returnMap);
    }

}

