package io.renren.modules.performance.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import io.renren.common.utils.*;
import io.renren.modules.performance.entity.PerformanceDoctorEntity;
import io.renren.modules.performance.entity.PerformanceFunctionEntity;
import io.renren.modules.performance.service.PerformanceDoctorService;
import io.renren.modules.performance.service.PerformanceFunctionService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysRoleEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.entity.SysUserRoleEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.performance.entity.PerformanceEntity;
import io.renren.modules.performance.service.PerformanceService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * 科研中心员工绩效考核评分表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-24 10:24:28
 */
@RestController
@RequestMapping("sys/performance")
public class PerformanceController extends AbstractController {
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private PerformanceDoctorService doctorService;
    @Autowired
    private PerformanceFunctionService functionService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserRoleService userRoleService;


    @Autowired
    private SysUserRoleService sysUserRoleService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:performance:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = performanceService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/listAll")
    public R listAll(){
        List<PerformanceEntity> list = performanceService.lambdaQuery().list();
        if (CollectionUtil.isNotEmpty(list)) {
            for (PerformanceEntity record : list) {
                Long userId = record.getUserId();
                SysUserEntity one = userService.lambdaQuery().eq(SysUserEntity::getUserId, userId).one();
                String username = one.getUsername();
                record.setUserName(username);
            }
        }
        return R.ok().put("page", list);
    }
    /**
     * 列表
     */
    @RequestMapping("/mylist")
    public R mylist(@RequestParam Map<String, Object> params){
        Long userId = getUserId();
        List<Long> roleIdList = userRoleService.queryRoleIdList(userId);
        if (roleIdList.contains(Long.parseLong("5"))) {
            List<SysUserRoleEntity> list = userRoleService.lambdaQuery().eq(SysUserRoleEntity::getRoleId, 1).list();
            List<Long> useIds = list.stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toList());
            PageUtils page = performanceService.queryPageByIds(useIds);
            return R.ok().put("page", page);
        }
        if (roleIdList.contains(Long.parseLong("6"))) {
            params.put("status", 5);
            PageUtils page = performanceService.queryPage(params);
            return R.ok().put("page", page);
        }
        if (roleIdList.contains(Long.parseLong("7"))) {
            params.put("status", 3);
            PageUtils page = performanceService.queryPage(params);
            return R.ok().put("page", page);
        }

        params.put("userId", userId);
        PageUtils page = performanceService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息 通过用户id
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:performance:info")
    public R info(@PathVariable("id") Long id){
        PerformanceEntity one = performanceService.lambdaQuery().eq(PerformanceEntity::getUserId, id)
                .one();
        if (one == null) {
            one = new PerformanceEntity();
        }
        SysUserEntity one1 = userService.lambdaQuery().eq(SysUserEntity::getUserId, id).one();
        one.setUserName(one1.getUsername());
        one.setUserId(id);
        Long userId = getUserId();
        one.setCreateUserId(userId);
        return R.ok().put("performance", one);
    }

    /**
     * 信息通过id
     */
    @RequestMapping("/infobyid/{id}")
    public R infobyid(@PathVariable("id") Long id){
        PerformanceEntity one = performanceService.lambdaQuery().eq(PerformanceEntity::getId, id)
                .one();
        if (one == null) {
            one = new PerformanceEntity();
        }
        return R.ok().put("performance", one);
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:performance:save")
    public R save(@RequestBody PerformanceEntity performance){
        performance.setCreateTime(new Date());
        performance.setStatus(1);
        performanceService.save(performance);
        performanceService.autorange();
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:performance:update")
    public R update(@RequestBody PerformanceEntity performance){
        performanceService.updateById(performance);
        performanceService.autorange();
        return R.ok();
    }

    /**
     * 自审
     */
    @RequestMapping("/savemy")
    @RequiresPermissions("sys:performance:save")
    public R savemy(@RequestBody PerformanceEntity performance){
        performance.setCreateTime(new Date());
        performance.setStatus(1);
        performance.setUserId(getUserId());
        performance.setCreateUserId(getUserId());
        performanceService.save(performance);
        performanceService.autorange();
        return R.ok();
    }

    /**
     * 管理部审核
     */
    @RequestMapping("/shenhe/{id}/{status}")
    @RequiresPermissions("sys:performance:shenhe")
    public R shenhe(@PathVariable("id") Long id,
                    @PathVariable("status") Integer status){
        performanceService.lambdaUpdate().set(PerformanceEntity::getStatus, status).
                set(PerformanceEntity::getCheckUserId, getUserId()).eq(PerformanceEntity::getId, id).update();
        return R.ok();
    }
    /**
     * 管理部审核
     */
    @RequestMapping("/rlshenhe/{id}/{status}")
    @RequiresPermissions("sys:performance:rlshenhe")
    public R rlshenhe(@PathVariable("id") Long id,
                      @PathVariable("status") Integer status){
        performanceService.lambdaUpdate().set(PerformanceEntity::getStatus, status).
                set(PerformanceEntity::getCheckUserId, getUserId()).eq(PerformanceEntity::getId, id).update();
        return R.ok();
    }
    /**
     * 管理部审核
     */
    @RequestMapping("/yuanshenhe/{id}/{status}")
    @RequiresPermissions("sys:performance:yuanshenhe")
    public R yuanshenhe(@PathVariable("id") Long id,
                        @PathVariable("status") Integer status){
        performanceService.lambdaUpdate().set(PerformanceEntity::getStatus, status).
                set(PerformanceEntity::getCheckUserId, getUserId()).eq(PerformanceEntity::getId, id).update();
        return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/updatemy")
    @RequiresPermissions("sys:performance:update")
    public R updatemy(@RequestBody PerformanceEntity performance){
        performanceService.updateById(performance);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:performance:delete")
    public R delete(@RequestBody Long[] ids){
        performanceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 导出模板
     */
    @GetMapping("/export")
    public void export(String date,
                       HttpServletResponse response) throws IOException {
        System.out.println(date);
        ArrayList<PerformanceEntity> performanceEntities = new ArrayList<>();
        SysUserEntity user = getUser();
        HashMap<String, Object> params = new HashMap<>();
        List<SysUserRoleEntity> list = sysUserRoleService.lambdaQuery().eq(SysUserRoleEntity::getUserId, user.getUserId()).list();
        List<Long> roleIdList = list.stream().map(SysUserRoleEntity::getRoleId).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(roleIdList)) {

        }else if (roleIdList.contains(new Long("2"))) {
            List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 10).list();
            List<Long> userIds = list1.stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toList());
            if (getUserId() != Constant.SUPER_ADMIN) {
                params.put("userIds", userIds);
            }
        }else if (roleIdList.contains(new Long("11"))) {
            List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 12).list();
            List<Long> userIds = list1.stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toList());
            if (getUserId() != Constant.SUPER_ADMIN) {
                params.put("userIds", userIds);
            }
        }
        else if (roleIdList.contains(new Long("13"))) {
            List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 14).list();
            List<Long> userIds = list1.stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toList());
            if (getUserId() != Constant.SUPER_ADMIN) {
                params.put("userIds", userIds);
            }
        }
        else if (roleIdList.contains(new Long("15"))) {
            List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 16).list();
            List<Long> userIds = list1.stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toList());
            if (getUserId() != Constant.SUPER_ADMIN) {
                params.put("userIds", userIds);
            }
        }
        else if (roleIdList.contains(new Long("17"))) {
            List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 18).list();
            List<Long> userIds = list1.stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toList());
            if (getUserId() != Constant.SUPER_ADMIN) {
                params.put("userIds", userIds);
            }
        }else if (roleIdList.contains(new Long("19"))) {
            List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 20).list();
            List<Long> userIds = list1.stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toList());
            if (getUserId() != Constant.SUPER_ADMIN) {
                params.put("userIds", userIds);
            }
        }
        else if (roleIdList.contains(new Long("21"))) {
            List<SysUserRoleEntity> list1 = sysUserRoleService.lambdaQuery().select(SysUserRoleEntity::getUserId).eq(SysUserRoleEntity::getRoleId, 22).list();
            List<Long> userIds = list1.stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toList());
            if (getUserId() != Constant.SUPER_ADMIN) {
                params.put("userIds", userIds);
            }
        }
        List userids = (ArrayList)params.get("userIds");
        List<PerformanceEntity> record = performanceService.lambdaQuery()
                .in(CollectionUtil.isNotEmpty(userids), PerformanceEntity::getUserId, userids)
                .like(date != null, PerformanceEntity::getCreateTime, date).list();
        record.forEach(x->{
            SysUserEntity one = userService.lambdaQuery().eq(SysUserEntity::getUserId, x.getUserId()).one();
            x.setUserName(one.getUsername());
        });
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("业绩审核模板"+date, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), PerformanceEntity.class).sheet("模板").doWrite(record);
    }
    /**
     * 上传用户信息
     */
    @PostMapping("/uploadExcel")
    public R uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), PerformanceEntity.class, new PerformanceListener(performanceService,userService,getUser())).sheet().doRead();
        return R.ok();
    }
    /**
     * data
     */
    @GetMapping("/echarts")
    public R echartsdata() throws IOException {
        Integer count1 = userRoleService.lambdaQuery().eq(SysUserRoleEntity::getRoleId, 1).count();
        Integer count2 = userRoleService.lambdaQuery().eq(SysUserRoleEntity::getRoleId, 2).count();
        Integer count3 = userRoleService.lambdaQuery().eq(SysUserRoleEntity::getRoleId, 3).count();
        Integer count4 = userRoleService.lambdaQuery().eq(SysUserRoleEntity::getRoleId, 4).count();
        Integer count5 = userRoleService.lambdaQuery().eq(SysUserRoleEntity::getRoleId, 5).count();
        Integer count6 = userRoleService.lambdaQuery().eq(SysUserRoleEntity::getRoleId, 6).count();
        Integer count7 = userRoleService.lambdaQuery().eq(SysUserRoleEntity::getRoleId, 7).count();
        Integer count9 = userRoleService.lambdaQuery().eq(SysUserRoleEntity::getRoleId, 9).count();
        Integer count10 = userRoleService.lambdaQuery().eq(SysUserRoleEntity::getRoleId, 10).count();
        ArrayList<Integer> data = new ArrayList<>();
        data.add(count1);
        data.add(count2);
        data.add(count3);
        data.add(count4);
        data.add(count5);
        data.add(count6);
        data.add(count7);
        data.add(count9);
        data.add(count10);
        return R.ok().put("data",data);
    }
    /**
     * data
     */
    @GetMapping("/echartstu")
    public R echartstu() throws IOException {
        Integer bohui1 = performanceService.lambdaQuery().eq(PerformanceEntity::getStatus, 2).count();
        Integer pass1 = performanceService.lambdaQuery().ne(PerformanceEntity::getStatus, 2).count();
        Integer bohui2 = doctorService.lambdaQuery().eq(PerformanceDoctorEntity::getStatus, 2).count();
        Integer pass2 = doctorService.lambdaQuery().ne(PerformanceDoctorEntity::getStatus, 2).count();
        Integer bohui3 = functionService.lambdaQuery().eq(PerformanceFunctionEntity::getStatus, 2).count();
        Integer pass3 = functionService.lambdaQuery().ne(PerformanceFunctionEntity::getStatus, 2).count();
        Integer data1 = bohui1+bohui2+bohui3;
        Integer data2 = pass1+pass2+pass3;
        ArrayList<Integer> data = new ArrayList<>();
        data.add(data2);
        data.add(data1);
        return R.ok().put("data",data);
    }
    /**
     * data
     */
    @GetMapping("/echartScore")
    public R echartScore() throws IOException {
        Integer a = performanceService.lambdaQuery().ge(PerformanceEntity::getExamine, 90).count();
        Integer b = performanceService.lambdaQuery().gt(PerformanceEntity::getExamine, 80).lt(PerformanceEntity::getExamine,90).count();
        Integer c = performanceService.lambdaQuery().le(PerformanceEntity::getExamine, 80).count();

        ArrayList<Integer> data = new ArrayList<>();
        data.add(a);
        data.add(b);
        data.add(c);
        return R.ok().put("data",data);
    }
}
