package io.renren.modules.ut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.ut.VO.UTWriteVO;
import io.renren.modules.ut.dao.UserPerformanceDao;
import io.renren.modules.ut.dao.WriteDao;
import io.renren.modules.ut.entity.UTWrite;
import io.renren.modules.ut.entity.UserPerformance;
import io.renren.modules.ut.service.UserPerformanceService;
import io.renren.modules.ut.service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service("UserPerformanceService")
public class UserPerformanceServiceImpl extends ServiceImpl<UserPerformanceDao, UserPerformance> implements UserPerformanceService {


}
