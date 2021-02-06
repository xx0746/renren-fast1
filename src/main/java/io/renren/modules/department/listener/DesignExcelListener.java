package io.renren.modules.department.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.department.entity.DesignCenter;
import io.renren.modules.department.entity.StrategyCenter;
import io.renren.modules.department.service.DesignCenterService;

import java.util.ArrayList;
import java.util.List;

public class DesignExcelListener extends AnalysisEventListener<DesignCenter>{

    private DesignCenterService designCenterService;

    private String createTime = "";

    private List<DesignCenter> designCenterList = new ArrayList<>();

    public DesignExcelListener(DesignCenterService designCenterService,String createTime){
        this.designCenterService = designCenterService;
        this.createTime = createTime;
    }

    @Override
    public void invoke(DesignCenter designCenter, AnalysisContext analysisContext) {
        if (designCenter.getName() != null && designCenter.getWordLoad() != null) {

            int workLoad = Integer.parseInt(designCenter.getWordLoad());
            int knowledgeSkills = Integer.parseInt(designCenter.getKnowledgeSkills());
            int communication = Integer.parseInt(designCenter.getCommunicationCollaboration());
            int jobResponse = Integer.parseInt(designCenter.getJobResponsibility());
            int learningInnovation = Integer.parseInt(designCenter.getLearningInnovation());
            int workingDiscipline = Integer.parseInt(designCenter.getWorkingDiscipline());
            designCenter.setEvaluationResult(String.valueOf(workLoad + knowledgeSkills + communication + jobResponse + learningInnovation + workingDiscipline));
            designCenter.setCreateTime(createTime);
            designCenterList.add(designCenter);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (designCenterList != null && designCenterList.size() > 0) {

            designCenterList.sort((o1, o2) -> {
                return Integer.parseInt(o2.getEvaluationResult()) - Integer.parseInt(o1.getEvaluationResult());
            });

            for (int i = 0; i < designCenterList.size(); i++ ) {
                if (Integer.parseInt(designCenterList.get(i).getEvaluationResult()) >= 90) {
                        designCenterList.get(i).setEvaluationLevel("优秀");
                        designCenterList.get(i).setPerformance("1.3");
                } else if (Integer.parseInt(designCenterList.get(i).getEvaluationResult()) >= 80) {
                        designCenterList.get(i).setEvaluationLevel("良好");
                        designCenterList.get(i).setPerformance("1.1");
                } else if (Integer.parseInt(designCenterList.get(i).getEvaluationResult()) >= 70) {
                    designCenterList.get(i).setEvaluationLevel("称职");
                    designCenterList.get(i).setPerformance("1.0");
                } else if (Integer.parseInt(designCenterList.get(i).getEvaluationResult()) >= 60) {
                    designCenterList.get(i).setEvaluationLevel("基本称职");
                    designCenterList.get(i).setPerformance("0.8");
                } else {
                    designCenterList.get(i).setEvaluationLevel("不称职");
                    designCenterList.get(i).setPerformance("0.1");
                }
            }
            designCenterService.saveBatch(designCenterList);
            designCenterList.clear();
        }
    }
}
