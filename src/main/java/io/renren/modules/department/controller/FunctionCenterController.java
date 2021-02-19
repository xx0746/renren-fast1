package io.renren.modules.department.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.utils.R;
import io.renren.modules.department.entity.FunctionCenter;
import io.renren.modules.department.entity.VO.CommentVO;
import io.renren.modules.department.listener.FunctionExcelListener;
import io.renren.modules.department.service.FunctionCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 职能部门 前端控制器
 * </p>
 *
 * @author java
 * @since 2021-02-04
 */
@RestController
//@CrossOrigin
@RequestMapping("/department/function")
public class FunctionCenterController {
    @Autowired
    private FunctionCenterService functionCenterService;

    //通过excel添加职能部门信息
    @PostMapping("/uploadExcel")
    public R uploadExcel(@RequestParam("file") MultipartFile file,String createTime) throws IOException {
        //删除重复数据
        functionCenterService.remove(new QueryWrapper<FunctionCenter>()
                .like("create_time",createTime));
        EasyExcel.read(file.getInputStream(), FunctionCenter.class,new FunctionExcelListener(functionCenterService,createTime)).sheet()
                .headRowNumber(2)
                .doRead();
        return R.ok();
    }

    //分页查询,附带条件查询
    @PostMapping("/pageListWithCondition")
    public R pageListWithCondition(CommentVO commentVO, Integer current, Integer pageSize){
        Page<FunctionCenter> functionPage = functionCenterService.pageListWithCondition(commentVO, current, pageSize);
        return R.ok().put("page",functionPage);
    }

    //导出excel
    @GetMapping("/exportExcel")
    public void exportExcel(CommentVO commentVO, HttpServletResponse response) {
        functionCenterService.exportExcel(commentVO,response);
        //return R.ok();
    }

}

