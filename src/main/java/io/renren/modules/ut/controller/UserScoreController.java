package io.renren.modules.ut.controller;

import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.ut.entity.UserScore;
import io.renren.modules.ut.service.UserScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userScore")
public class UserScoreController extends AbstractController {
    @Autowired
    private UserScoreService userScoreService;
    private Logger logger = LoggerFactory.getLogger(UserScoreController.class);

    /**
     * 查询员工积分列表
     */
    @PostMapping("/getUserScoreList")
    public R getUserScoreList(@RequestBody Map<String, Object> params) {
        SysUserEntity user = getUser();
        if(user.getUserId()!=(long) Constant.SUPER_ADMIN){
            params.put("departmentId",user.getDepartmentId());
        }
        Map<String, Object> result = userScoreService.getUserScoreList(params);
        return R.ok(result);
    }
    /**
     * 保存员工积分
     */
    @PostMapping("/saveUserScore")
    public R saveUserScore(@RequestBody Map<String, Object> params) {
        List<Map> list = (List<Map>)params.get("dataList");
        List<UserScore> userScoreList = new ArrayList<>();
        for(Map map : list){
            UserScore userScore = new UserScore();
            userScore.setUserId(((Integer)map.get("userId")).longValue());
            userScore.setDepartmentId(((Integer)map.get("departmentId")).longValue());
            userScore.setYear((String)map.get("year"));
            userScore.setMonth((String)map.get("month"));
            String score = String.valueOf(map.get("score"));
            userScore.setScore(Double.parseDouble(score));
            userScore.setCreateTime(new Date());
            userScore.setModifiedTime(new Date());
            userScoreList.add(userScore);
        }
        if(userScoreList.size()>0){
            userScoreService.saveBatch(userScoreList);
        }
        return R.ok();
    }
}
