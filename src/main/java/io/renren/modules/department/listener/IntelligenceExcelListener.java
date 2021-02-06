package io.renren.modules.department.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.department.entity.DesignCenter;
import io.renren.modules.department.entity.IntelligenceCenter;
import io.renren.modules.department.service.IntelligenceCenterService;

import java.util.ArrayList;
import java.util.List;

public class IntelligenceExcelListener extends AnalysisEventListener<IntelligenceCenter> {

    private List<IntelligenceCenter> intelligenceCenterList = new ArrayList<>();

    private IntelligenceCenterService intelligenceCenterService;

    private String createTime = "";

    public IntelligenceExcelListener(IntelligenceCenterService intelligenceCenterService,String createTime){
        this.intelligenceCenterService = intelligenceCenterService;
        this.createTime = createTime;
    }

    @Override
    public void invoke(IntelligenceCenter data, AnalysisContext context) {
        if (data.getName() != null && data.getWordLoad() != null) {

            int workLoad = Integer.parseInt(data.getWordLoad());
            int knowledgeSkills = Integer.parseInt(data.getKnowledgeSkills());
            int communication = Integer.parseInt(data.getCommunicationCollaboration());
            int jobResponse = Integer.parseInt(data.getJobResponsibility());
            int learningInnovation = Integer.parseInt(data.getLearningInnovation());
            int workingDiscipline = Integer.parseInt(data.getWorkingDiscipline());
            int progressController = Integer.parseInt(data.getProgressController());
            int resultValue = Integer.parseInt(data.getResultValue());
            int advanceInnovation = Integer.parseInt(data.getAdvancedInnovation());
            int investigate = Integer.parseInt(data.getInvestigate());


            data.setEvaluationResult(String.valueOf(workLoad + knowledgeSkills + communication + jobResponse + learningInnovation + workingDiscipline + progressController + resultValue + advanceInnovation + investigate));
            data.setCreateTime(createTime);
            intelligenceCenterList.add(data);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (intelligenceCenterList != null && intelligenceCenterList.size() > 0) {

            intelligenceCenterList.sort((o1, o2) -> {
                return Integer.parseInt(o2.getEvaluationResult()) - Integer.parseInt(o1.getEvaluationResult());
            });

            for (int i = 0; i < intelligenceCenterList.size(); i++ ) {
                if (Integer.parseInt(intelligenceCenterList.get(i).getEvaluationResult()) >= 90) {
                    intelligenceCenterList.get(i).setEvaluationLevel("优秀");
                    intelligenceCenterList.get(i).setPerformance("1.3");
                } else if (Integer.parseInt(intelligenceCenterList.get(i).getEvaluationResult()) >= 80) {
                    intelligenceCenterList.get(i).setEvaluationLevel("良好");
                    intelligenceCenterList.get(i).setPerformance("1.1");
                } else if (Integer.parseInt(intelligenceCenterList.get(i).getEvaluationResult()) >= 70) {
                    intelligenceCenterList.get(i).setEvaluationLevel("称职");
                    intelligenceCenterList.get(i).setPerformance("1.0");
                } else if (Integer.parseInt(intelligenceCenterList.get(i).getEvaluationResult()) >= 60) {
                    intelligenceCenterList.get(i).setEvaluationLevel("基本称职");
                    intelligenceCenterList.get(i).setPerformance("0.8");
                } else {
                    intelligenceCenterList.get(i).setEvaluationLevel("不称职");
                    intelligenceCenterList.get(i).setPerformance("0.1");
                }
            }

            intelligenceCenterService.saveBatch(intelligenceCenterList);
            intelligenceCenterList.clear();
        }
    }
}
