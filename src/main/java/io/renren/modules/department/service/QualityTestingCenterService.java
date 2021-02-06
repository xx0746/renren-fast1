package io.renren.modules.department.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.department.entity.QualityTestingCenter;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.department.entity.VO.CommentVO;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 质检中心 服务类
 * </p>
 *
 * @author java
 * @since 2021-02-04
 */
public interface QualityTestingCenterService extends IService<QualityTestingCenter> {

    Page<QualityTestingCenter> pageListWithCondition(CommentVO commentVO, Integer current, Integer pageSize);

    void exportExcel(CommentVO commentVO, HttpServletResponse response);
}
