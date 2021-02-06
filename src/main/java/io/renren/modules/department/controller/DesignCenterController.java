package io.renren.modules.department.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.utils.R;
import io.renren.modules.department.entity.DesignCenter;
import io.renren.modules.department.entity.VO.CommentVO;
import io.renren.modules.department.listener.DesignExcelListener;
import io.renren.modules.department.service.DesignCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 设计中心 前端控制器
 * </p>
 *
 * @author java
 * @since 2021-02-04
 */
@RestController
//@CrossOrigin
@RequestMapping("/department/design")
public class DesignCenterController {
    @Autowired
    private DesignCenterService designCenterService;

    //通过excel添加设计部门信息
    @PostMapping("/uploadExcel")
    public R uploadExcel(@RequestParam("file") MultipartFile file,String createTime) throws IOException {
        //删除重复数据
        designCenterService.remove(new QueryWrapper<DesignCenter>()
        .like("create_time",createTime));
        EasyExcel.read(file.getInputStream(), DesignCenter.class,new DesignExcelListener(designCenterService,createTime)).sheet()
                .headRowNumber(6)
                .doRead();
        return R.ok();
    }

    //分页查询,附带条件查询
    @PostMapping("/pageListWithCondition")
    public R pageListWithCondition(CommentVO commentVO, Integer current, Integer pageSize){
        Page<DesignCenter> designPage = designCenterService.pageListWithCondition(commentVO, current, pageSize);
        return R.ok().put("page",designPage);
    }

    //导出excel
    @GetMapping("/exportExcel")
    public void exportExcel(CommentVO commentVO, HttpServletResponse response) {
        designCenterService.exportExcel(commentVO,response);
        //return R.ok();
    }

}

