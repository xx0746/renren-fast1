package io.renren.modules.ut.controller;


import io.renren.common.utils.R;
import io.renren.modules.ut.entity.WorkType;
import io.renren.modules.ut.service.WorkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.coyote.http11.Constants.a;

/**
 * <p>
 * 工作类型 前端控制器
 * </p>
 *
 * @author java
 * @since 2021-11-27
 */
@RestController
@RequestMapping("/wang/test")
public class WangTestController {


    @PostMapping("mytest")
    public R mytest(@RequestBody Map<String, Object> params) {

        //int i = a + 1;
        //Math.


        System.out.println(params.toString());
        System.out.println("测试中");

        return R.ok().put("dataList","测试中");
    }
}

