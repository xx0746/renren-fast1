package io.renren.modules.performance.controller;

import java.util.*;

import cn.hutool.core.collection.CollectionUtil;
import io.renren.common.utils.Constant;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.service.SysUserRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.performance.entity.PerformanceDoctorEntity;
import io.renren.modules.performance.service.PerformanceDoctorService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 专家博士绩效考核
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-24 17:48:16
 */
@RestController
@RequestMapping("sys/performancedoctor")
public class PerformanceDoctorController extends AbstractController {
    @Autowired
    private PerformanceDoctorService performanceDoctorService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:performancedoctor:list")
    public R list(@RequestParam Map<String, Object> params){
        Long userId = getUserId();
        List<Long> roles = sysUserRoleService.queryRoleIdList(userId);
        if (CollectionUtil.isEmpty(roles)) {

        } else if (CollectionUtil.isNotEmpty(roles) && roles.contains(Long.parseLong("3"))) {
            if (userId != Constant.SUPER_ADMIN) {
                params.put("userId", userId);
            }
        }else if (CollectionUtil.isNotEmpty(roles) && roles.contains(Long.parseLong("1"))) {
            ArrayList<Integer> status = new ArrayList<>();
            status.add(1);
            status.add(3);
            PageUtils page = performanceDoctorService.queryPageByStatus(status);
            return R.ok().put("page", page);
        }else if (CollectionUtil.isNotEmpty(roles) && roles.contains(Long.parseLong("5"))) {
            ArrayList<Integer> status = new ArrayList<>();
            status.add(3);
            PageUtils page = performanceDoctorService.queryPageByStatus(status);
            return R.ok().put("page", page);
        }else if (CollectionUtil.isNotEmpty(roles) && roles.contains(Long.parseLong("6"))) {
            ArrayList<Integer> status = new ArrayList<>();
            status.add(7);
            PageUtils page = performanceDoctorService.queryPageByStatus(status);
            return R.ok().put("page", page);
        }else if (CollectionUtil.isNotEmpty(roles) && roles.contains(Long.parseLong("7"))) {
            ArrayList<Integer> status = new ArrayList<>();
            status.add(5);
            PageUtils page = performanceDoctorService.queryPageByStatus(status);
            return R.ok().put("page", page);
        }
        PageUtils page = performanceDoctorService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:performancedoctor:info")
    public R info(@PathVariable("id") Long id){
		PerformanceDoctorEntity performanceDoctor = performanceDoctorService.getById(id);

        return R.ok().put("performanceDoctor", performanceDoctor);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:performancedoctor:save")
    public R save(@RequestBody PerformanceDoctorEntity performanceDoctor){
        performanceDoctor.setCreateTime(new Date());
        performanceDoctor.setUserId(getUserId());
        performanceDoctor.setStatus(1);
		performanceDoctorService.save(performanceDoctor);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:performancedoctor:update")
    public R update(@RequestBody PerformanceDoctorEntity performanceDoctor){
		performanceDoctorService.updateById(performanceDoctor);

        return R.ok();
    }
    /**
     * 审核
     */
    @RequestMapping("/shenhe")
    @RequiresPermissions("sys:performancedoctor:shenhe")
    public R shenhe(@RequestBody PerformanceDoctorEntity performanceDoctor){
        performanceDoctorService.updateById(performanceDoctor);
        return R.ok();
    }
    /**
     * 审核
     */
    @RequestMapping("/shenhe2")
    @RequiresPermissions("sys:performancedoctor:shenhe2")
    public R shenhe2(@RequestBody PerformanceDoctorEntity performanceDoctor){
        performanceDoctorService.updateById(performanceDoctor);
        return R.ok();
    }
    /**
     * 审核
     */
    @RequestMapping("/rlshenhe")
    @RequiresPermissions("sys:performancedoctor:rlshenhe")
    public R rlshenhe(@RequestBody PerformanceDoctorEntity performanceDoctor){
        performanceDoctorService.updateById(performanceDoctor);
        return R.ok();
    }
    /**
     * 审核
     */
    @RequestMapping("/yuanshenhe")
    @RequiresPermissions("sys:performancedoctor:yuanshenhe")
    public R yuanshenhe(@RequestBody PerformanceDoctorEntity performanceDoctor){
        performanceDoctorService.updateById(performanceDoctor);
        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:performancedoctor:delete")
    public R delete(@RequestBody Long[] ids){
		performanceDoctorService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
