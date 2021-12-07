package io.renren.modules.ut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.ut.entity.UserScore;

import java.util.Map;

public interface UserScoreService extends IService<UserScore> {
    Map<String, Object> getUserScoreList(Map<String,Object> queryMap);
}
