package io.renren.modules.department.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.department.entity.DesignCenter;
import io.renren.modules.department.entity.VO.CommentVO;
import io.renren.modules.department.mapper.DesignCenterMapper;
import io.renren.modules.department.service.DesignCenterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 设计中心 服务实现类
 * </p>
 *
 * @author java
 * @since 2021-02-04
 */
@Service
public class DesignCenterServiceImpl extends ServiceImpl<DesignCenterMapper, DesignCenter> implements DesignCenterService {

    @Override
    public Page<DesignCenter> pageListWithCondition(CommentVO commentVO, Integer current, Integer pageSize) {
        Page<DesignCenter> page = new Page<>(current,pageSize);
        //构建查询条件
        QueryWrapper<DesignCenter> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(commentVO.getName()),"name",commentVO.getName())
                .like(StringUtils.isNotEmpty(commentVO.getCreateTime()),"create_time",commentVO.getCreateTime())
                .orderByAsc("sortId+0");
        Page<DesignCenter> designPage = baseMapper.selectPage(page, wrapper);
        designPage.getRecords().sort((o1, o2) -> {
            return Integer.parseInt(o1.getSortId()) - Integer.parseInt(o2.getSortId());
        });
        return designPage;
    }

    @Override
    public void exportExcel(CommentVO commentVO, HttpServletResponse response) {
        //首先查询符合条件的数据
        QueryWrapper<DesignCenter> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(commentVO.getName()),"name",commentVO.getName())
                .like(StringUtils.isNotEmpty(commentVO.getCreateTime()),"create_time",commentVO.getCreateTime())
        .orderByAsc("sortId+0");
        List<DesignCenter> designList = baseMapper.selectList(wrapper);
        //通过查询出来的数据写到浏览器

        //设置内容类型
        response.setContentType("application/vnd.ms-excel");
        //设置字符编码
        response.setCharacterEncoding("utf-8");
        //设置类容描述,也就是文件名
        String fileName = UUID.randomUUID().toString()+".xlsx";
        response.setHeader("Content-disposition","attachment;filename="+fileName);
        //向浏览器写入数据
        try {
            EasyExcel.write(response.getOutputStream(),DesignCenter.class).sheet("模板").doWrite(designList);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
