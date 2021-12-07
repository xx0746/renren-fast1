package io.renren.modules.ut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.ut.VO.UTWriteVO;
import io.renren.modules.ut.dao.WriteDao;
import io.renren.modules.ut.entity.UTWrite;
import io.renren.modules.ut.service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ut填报表 服务实现类
 * </p>
 *
 * @author java
 * @since 2021-11-27
 */
@Service("WriteService")
public class WriteServiceImpl extends ServiceImpl<WriteDao, UTWrite> implements WriteService {

    @Autowired
    private SysUserService sysUserService;
    @Override
    public Map<String, Object> writeList(Long userId, Long current, Long size, String year, String month, String week, Long projectId) {
        List<UTWriteVO> utWriteVOS = baseMapper.writeList(userId, (current - 1) * size, size, year, month, week, projectId);
        Long total = baseMapper.writeListCount(userId, year, month, week, projectId);

        long usedUT = 0;
        int status = 0;
        for (UTWriteVO utWriteVO : utWriteVOS) {
            status = utWriteVO.getStatus();
            usedUT += utWriteVO.getUt() == null ? 0 : utWriteVO.getUt();
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("msg", "success");
        returnMap.put("current", current);
        returnMap.put("total", total);
        returnMap.put("size", utWriteVOS.size());
        returnMap.put("usedUt", usedUT);
        returnMap.put("status", status);
        returnMap.put("dataList", utWriteVOS);
        return returnMap;
    }
    @Override
    public Map<String, Object> writeProjectList(SysUserEntity user, Long current, Long size) {
        List<Map> userUTVOList = baseMapper.writeProjectList(user.getUserId(), (current - 1) * size, size);
        Long total = baseMapper.projectCount(user.getUserId());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("msg", "success");
        returnMap.put("current", current);
        returnMap.put("total", total);
        returnMap.put("code", 0);
        returnMap.put("size", userUTVOList.size());
        returnMap.put("dataList", userUTVOList);
        return returnMap;
    }

}
