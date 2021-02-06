package io.renren.modules.department.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.utils.R;
import io.renren.modules.department.entity.DesignCenter;
import io.renren.modules.department.entity.FinancialCenter;
import io.renren.modules.department.entity.QualityTestingCenter;
import io.renren.modules.department.entity.VO.CommentVO;
import io.renren.modules.department.listener.FinancialExcelListener;
import io.renren.modules.department.listener.QualityTestingExcelListener;
import io.renren.modules.department.service.QualityTestingCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 质检中心 前端控制器
 * </p>
 *
 * @author java
 * @since 2021-02-04
 */
@RestController
@RequestMapping("/department/qualityTesting")
public class QualityTestingCenterController {

    @Autowired
    private QualityTestingCenterService qualityTestingCenterService;

    @PostMapping("/uploadExcel")
    public R uploadExcel(@RequestParam("file") MultipartFile file,String createTime) throws IOException {
        //删除重复数据
        qualityTestingCenterService.remove(new QueryWrapper<QualityTestingCenter>()
                .like("create_time",createTime));
        EasyExcel.read(file.getInputStream(), QualityTestingCenter.class,new QualityTestingExcelListener(qualityTestingCenterService,createTime)).sheet().headRowNumber(6).doRead();
        return R.ok();
    }

    //分页查询附带条件筛选
    @PostMapping("/pageListWithCondition")
    public R pageListWithCondition(CommentVO commentVO,Integer current,Integer pageSize){
        Page<QualityTestingCenter> qualityTestPage = qualityTestingCenterService.pageListWithCondition(commentVO,current,pageSize);
        return R.ok().put("page",qualityTestPage);
    }


    //导出excel
    @GetMapping("/exportExcel")
    public void exportExcel(CommentVO commentVO, HttpServletResponse response) {
        qualityTestingCenterService.exportExcel(commentVO,response);
        //return R.ok();
    }
}

