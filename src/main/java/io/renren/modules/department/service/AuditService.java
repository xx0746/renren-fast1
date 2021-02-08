package io.renren.modules.department.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.department.entity.Audit;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.department.entity.VO.CommentVO;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Audit 服务类
 * </p>
 *
 * @author java
 * @since 2021-02-04
 */
public interface AuditService extends IService<Audit> {

    Page<Audit> pageListWithCondition(CommentVO commentVO, Integer current, Integer pageSize);

    void exportExcel(CommentVO commentVO, HttpServletResponse response);
}
