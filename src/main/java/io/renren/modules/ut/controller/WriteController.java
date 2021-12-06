package io.renren.modules.ut.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.ut.VO.WriteVO;
import io.renren.modules.ut.entity.UTWrite;
import io.renren.modules.ut.service.WorkTypeService;
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
@RequestMapping("/utWrite")
public class WriteController extends AbstractController {
    @Autowired
    private WriteService writeService; //UT填报的service

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
        Map<String, Object> returnMap = writeService.writeProjectList(user, current, size);
        return R.ok(returnMap);
    }
    /**
     * 3.2
     */
    @PostMapping("/writeList")
    public R writeList(@RequestParam("current") Long current, @RequestParam("size") Long size, @RequestParam("year") String year,
                       @RequestParam("month") String month, @RequestParam("week") String week, @RequestParam("projectId") Long projectId) {
        Long userId = getUserId();
        Map<String, Object> returnMap = writeService.writeList(userId, current, size, year, month, week, projectId);
        return R.ok(returnMap);
    }
    /**
     * 3.4
     */
    @PostMapping("/writeAdd")
    public R writeAdd(@RequestBody WriteVO writeVO) {
        QueryWrapper<UTWrite> utWriteQueryWrapper = new QueryWrapper<>();
        utWriteQueryWrapper.eq(writeVO.getYear() != null, "year", writeVO.getYear());
        utWriteQueryWrapper.eq(writeVO.getMonth() != null, "month", writeVO.getMonth());
        utWriteQueryWrapper.eq(writeVO.getWeek() != null, "week", writeVO.getWeek());
        utWriteQueryWrapper.eq(writeVO.getProjectId() != null, "project_id", writeVO.getProjectId());
        utWriteQueryWrapper.eq("user_id", getUserId());
        List<UTWrite> list = writeService.list(utWriteQueryWrapper);
        List<Long> ids = new ArrayList<>();
        for(UTWrite write :list){
            int status = write.getStatus();
            ids.add(write.getId());
            if(status !=0){
                return R.error("该周工时已经在审批中，无法操作");
            }
        }
        if(list.size()>0){
            writeService.removeByIds(ids);
        }
        List<UTWrite> writes = writeVO.getDataList().stream().map(entry -> {
            UTWrite write = new UTWrite();
            SysUserEntity user = getUser();
            write.setUserId(user.getUserId());
            write.setDepartmentId(user.getDepartmentId());
            write.setProjectId(writeVO.getProjectId());
            write.setYear(writeVO.getYear());
            write.setMonth(writeVO.getMonth());
            write.setWeek(writeVO.getWeek());
            write.setYearmonth(Long.parseLong(writeVO.getYear()+writeVO.getMonth()));
            write.setYearMonthWeek(Long.parseLong(writeVO.getYear()+writeVO.getMonth()+writeVO.getWeek()));
            write.setOneId(entry.getOneId());
            write.setTwoId(entry.getTwoId());
            write.setUt(entry.getUt());
            write.setCreateTime(new Date());
            write.setModifiedTime(new Date());
            //Write write = new Write(writeVO.getYear(), writeVO.getMonth(), writeVO.getWeek(), writeVO.getProjectId(), entry.getOneId(), entry.getTwoId(), getUserId(), entry.getUt());
            return write;
        }).collect(Collectors.toList());
        if(writes.size()>0){
            writeService.saveBatch(writes);
        }
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("code", 0);
        returnMap.put("msg", "success");
        return R.ok(returnMap);
    }

}

