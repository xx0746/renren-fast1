package io.renren.modules.ut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.ut.dao.UserScoreDao;
import io.renren.modules.ut.entity.UserScore;
import io.renren.modules.ut.service.UserScoreService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("UserScoreService")
public class UserScoreServiceImpl extends ServiceImpl<UserScoreDao, UserScore> implements UserScoreService {
    @Override
    public Map<String, Object> getUserScoreList(Map<String, Object> queryMap) {
        List<Map> list = baseMapper.getUserScoreList(queryMap);
        String isScoreResult = "0";
        for (Map map : list) {
            String isScore = (String) map.get("isScore");
            if ("1".equals(isScore)) {
                isScoreResult = isScore;
                break;
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("dataList", list);
        resultMap.put("isScore", isScoreResult);
        return resultMap;
    }
}
