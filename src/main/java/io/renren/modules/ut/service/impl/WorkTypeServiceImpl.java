package io.renren.modules.ut.service.impl;

import io.renren.modules.ut.dao.WorkTypeDao;
import io.renren.modules.ut.entity.WorkType;
import io.renren.modules.ut.service.WorkTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 工作类型 服务实现类
 * </p>
 *
 * @author java
 * @since 2021-11-27
 */
@Service("WorkTypeService")
public class WorkTypeServiceImpl extends ServiceImpl<WorkTypeDao, WorkType> implements WorkTypeService {

}
