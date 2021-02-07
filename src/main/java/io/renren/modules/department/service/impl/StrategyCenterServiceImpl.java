package io.renren.modules.department.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.department.entity.DesignCenter;
import io.renren.modules.department.entity.QualityTestingCenter;
import io.renren.modules.department.entity.StrategyCenter;
import io.renren.modules.department.entity.VO.CommentVO;
import io.renren.modules.department.mapper.StrategyCenterMapper;
import io.renren.modules.department.service.StrategyCenterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 战略中心 服务实现类
 * </p>
 *
 * @author java
 * @since 2021-02-04
 */
@Service
public class StrategyCenterServiceImpl extends ServiceImpl<StrategyCenterMapper, StrategyCenter> implements StrategyCenterService {

    @Override
    public Page<StrategyCenter> pageListWithCondition(CommentVO commentVO, Integer current, Integer pageSize) {
        Page<StrategyCenter> page = new Page<>(current,pageSize);
        //构建查询条件
        QueryWrapper<StrategyCenter> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(commentVO.getName()),"name",commentVO.getName())
                .like(StringUtils.isNotEmpty(commentVO.getCreateTime()),"create_time",commentVO.getCreateTime())
                .orderByAsc("sortId+0");
        Page<StrategyCenter> strategyPage = baseMapper.selectPage(page, wrapper);
        strategyPage.getRecords().sort((o1, o2) -> {
            return Integer.parseInt(o1.getSortId()) - Integer.parseInt(o2.getSortId());
        });
        return strategyPage;
    }

    @Override
    public void exportExcel(CommentVO commentVO, HttpServletResponse response) {
        //首先查询符合条件的数据
        QueryWrapper<StrategyCenter> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(commentVO.getName()),"name",commentVO.getName())
                .like(StringUtils.isNotEmpty(commentVO.getCreateTime()),"create_time",commentVO.getCreateTime())
                .orderByAsc("sortId+0");
        List<StrategyCenter> strategyList = baseMapper.selectList(wrapper);
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
            EasyExcel.write(response.getOutputStream(), StrategyCenter.class).sheet("模板").doWrite(strategyList);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
