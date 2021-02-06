package io.renren.modules.department.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.department.entity.DesignCenter;
import io.renren.modules.department.entity.QualityTestingCenter;
import io.renren.modules.department.service.QualityTestingCenterService;

import java.util.ArrayList;
import java.util.List;

public class QualityTestingExcelListener extends AnalysisEventListener<QualityTestingCenter> {

    private QualityTestingCenterService qualityTestingCenterService;

    private List<QualityTestingCenter> qualityTestingCenterList = new ArrayList<>();

    private String createTime = "";

    public QualityTestingExcelListener(QualityTestingCenterService qualityTestingCenterService,String createTime){
        this.qualityTestingCenterService = qualityTestingCenterService;
        this.createTime = createTime;
    }

    @Override
    public void invoke(QualityTestingCenter data, AnalysisContext context) {
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
            qualityTestingCenterList.add(data);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (qualityTestingCenterList != null && qualityTestingCenterList.size() > 0) {

            qualityTestingCenterList.sort((o1, o2) -> {
                return Integer.parseInt(o2.getEvaluationResult()) - Integer.parseInt(o1.getEvaluationResult());
            });

            for (int i = 0; i < qualityTestingCenterList.size(); i++ ) {
                if (Integer.parseInt(qualityTestingCenterList.get(i).getEvaluationResult()) >= 90) {
                    qualityTestingCenterList.get(i).setEvaluationLevel("优秀");
                    qualityTestingCenterList.get(i).setPerformance("1.3");
                } else if (Integer.parseInt(qualityTestingCenterList.get(i).getEvaluationResult()) >= 80) {
                    qualityTestingCenterList.get(i).setEvaluationLevel("良好");
                    qualityTestingCenterList.get(i).setPerformance("1.1");
                } else if (Integer.parseInt(qualityTestingCenterList.get(i).getEvaluationResult()) >= 70) {
                    qualityTestingCenterList.get(i).setEvaluationLevel("称职");
                    qualityTestingCenterList.get(i).setPerformance("1.0");
                } else if (Integer.parseInt(qualityTestingCenterList.get(i).getEvaluationResult()) >= 60) {
                    qualityTestingCenterList.get(i).setEvaluationLevel("基本称职");
                    qualityTestingCenterList.get(i).setPerformance("0.8");
                } else {
                    qualityTestingCenterList.get(i).setEvaluationLevel("不称职");
                    qualityTestingCenterList.get(i).setPerformance("0.1");
                }
            }

            qualityTestingCenterService.saveBatch(qualityTestingCenterList);
            qualityTestingCenterList.clear();
        }
    }
}
