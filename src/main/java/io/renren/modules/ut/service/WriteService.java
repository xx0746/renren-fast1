package io.renren.modules.ut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.ut.entity.UTWrite;

import java.util.Map;

/**
 * <p>
 * ut填报表 服务类
 * </p>
 *
 * @author java
 * @since 2021-11-27
 */
public interface WriteService extends IService<UTWrite> {
    Map<String, Object> writeList(Long userId, Long current, Long size, String year, String month, String week, Long projectId);
    Map<String, Object> writeProjectList(SysUserEntity user, Long current, Long size);
}
