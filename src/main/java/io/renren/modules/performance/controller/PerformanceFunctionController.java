package io.renren.modules.performance.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.excel.EasyExcel;
import io.renren.common.utils.PerformanceFunctionListener;
import io.renren.common.utils.PerformanceListener;
import io.renren.modules.performance.entity.PerformanceDoctorEntity;
import io.renren.modules.performance.entity.PerformanceEntity;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserRoleEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.performance.entity.PerformanceFunctionEntity;
import io.renren.modules.performance.service.PerformanceFunctionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * 职能部门员工绩效考核评分
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-24 17:48:16
 */
@RestController
@RequestMapping("sys/performancefunction")
public class PerformanceFunctionController extends AbstractController {
    @Autowired
    private PerformanceFunctionService performanceFunctionService;
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysUserRoleService userRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:performancefunction:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = performanceFunctionService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/mylist")
    public R mylist(@RequestParam Map<String, Object> params){
        Long userId = getUserId();
        List<SysUserRoleEntity> list = userRoleService.lambdaQuery().eq(SysUserRoleEntity::getUserId, userId).list();
        List<Long> roles = list.stream().map(SysUserRoleEntity::getRoleId).collect(Collectors.toList());
        System.out.println(list);
        if (roles.contains(Long.parseLong("4"))) {

        } else if (roles.contains(Long.parseLong("6"))) {
            params.put("status", 5);
        }
        else if (roles.contains(Long.parseLong("7"))) {
            params.put("status", 3);
        }else {
            params.put("userId", userId);
        }
        PageUtils page = performanceFunctionService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息通过id
     */
    @RequestMapping("/infobyid/{id}")
    public R infobyid(@PathVariable("id") Long id){
        PerformanceFunctionEntity one = performanceFunctionService.lambdaQuery().eq(PerformanceFunctionEntity::getId, id)
                .one();
        if (one == null) {
            one = new PerformanceFunctionEntity();
        }
        return R.ok().put("performanceFunction", one);
    }

    /**
     * 审核
     */
    @RequestMapping("/shenhe")
    @RequiresPermissions("sys:performancefunction:shenhe")
    public R shenhe(@RequestBody PerformanceFunctionEntity entity){
        performanceFunctionService.updateById(entity);
        return R.ok();
    }
    /**
     * 审核
     */
    @RequestMapping("/rlshenhe")
    @RequiresPermissions("sys:performancefunction:rlshenhe")
    public R rlshenhe(@RequestBody PerformanceFunctionEntity entity){
        performanceFunctionService.updateById(entity);
        return R.ok();
    }
    /**
     * 审核
     */
    @RequestMapping("/yuanshenhe")
    @RequiresPermissions("sys:performancefunction:yuanshenhe")
    public R yuanshenhe(@RequestBody PerformanceFunctionEntity entity){
        performanceFunctionService.updateById(entity);
        return R.ok();
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:performancefunction:info")
    public R info(@PathVariable("id") Long id){
        PerformanceFunctionEntity one = performanceFunctionService.lambdaQuery().eq(PerformanceFunctionEntity::getUserId, id)
                .one();
        if (one == null) {
            one = new PerformanceFunctionEntity();
        }
        SysUserEntity one1 = userService.lambdaQuery().eq(SysUserEntity::getUserId, id).one();
        one.setUserName(one1.getUsername());
        one.setUserId(id);
        return R.ok().put("performanceFunction", one);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:performancefunction:save")
    public R save(@RequestBody PerformanceFunctionEntity performanceFunction){
        performanceFunction.setCreateTime(new Date());
        performanceFunction.setStatus(1);
		performanceFunctionService.save(performanceFunction);
        return R.ok();
    }

    /**
     * 自审
     */
    @RequestMapping("/savemy")
    public R savemy(@RequestBody PerformanceFunctionEntity performanceFunction){
        performanceFunction.setCreateTime(new Date());
        performanceFunction.setStatus(1);
        performanceFunction.setUserId(getUserId());
        performanceFunctionService.save(performanceFunction);
        return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:performancefunction:update")
    public R update(@RequestBody PerformanceFunctionEntity performanceFunction){
		performanceFunctionService.updateById(performanceFunction);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:performancefunction:delete")
    public R delete(@RequestBody Long[] ids){
		performanceFunctionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 导出模板
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        ArrayList<PerformanceFunctionEntity> performanceEntities = new ArrayList<>();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("职能业绩审核模板", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), PerformanceFunctionEntity.class).sheet("模板").doWrite(performanceEntities);
    }
    /**
     * 上传用户信息
     */
    @PostMapping("/uploadExcel")
    public R uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), PerformanceFunctionEntity.class, new PerformanceFunctionListener(performanceFunctionService,userService)).sheet().doRead();
        return R.ok();
    }

}
