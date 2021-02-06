package io.renren.modules.department.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.department.entity.DesignCenter;
import io.renren.modules.department.entity.TechnologyCenter;
import io.renren.modules.department.service.TechnologyCenterService;

import java.util.ArrayList;
import java.util.List;

public class TechnologyExcelListener extends AnalysisEventListener<TechnologyCenter> {

    private TechnologyCenterService technologyCenterService;

    private List<TechnologyCenter> technologyCenterList = new ArrayList<>();

    private String createTime = "";

    public TechnologyExcelListener(TechnologyCenterService technologyCenterService,String createTime) {
        this.technologyCenterService = technologyCenterService;
        this.createTime = createTime;
    }

    @Override
    public void invoke(TechnologyCenter data, AnalysisContext context) {
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
            technologyCenterList.add(data);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (technologyCenterList != null && technologyCenterList.size() > 0) {

            technologyCenterList.sort((o1, o2) -> {
                return Integer.parseInt(o2.getEvaluationResult()) - Integer.parseInt(o1.getEvaluationResult());
            });

            for (int i = 0; i < technologyCenterList.size(); i++ ) {
                if (Integer.parseInt(technologyCenterList.get(i).getEvaluationResult()) >= 90) {
                    technologyCenterList.get(i).setEvaluationLevel("优秀");
                    technologyCenterList.get(i).setPerformance("1.3");
                } else if (Integer.parseInt(technologyCenterList.get(i).getEvaluationResult()) >= 80) {
                    technologyCenterList.get(i).setEvaluationLevel("良好");
                    technologyCenterList.get(i).setPerformance("1.1");
                } else if (Integer.parseInt(technologyCenterList.get(i).getEvaluationResult()) >= 70) {
                    technologyCenterList.get(i).setEvaluationLevel("称职");
                    technologyCenterList.get(i).setPerformance("1.0");
                } else if (Integer.parseInt(technologyCenterList.get(i).getEvaluationResult()) >= 60) {
                    technologyCenterList.get(i).setEvaluationLevel("基本称职");
                    technologyCenterList.get(i).setPerformance("0.8");
                } else {
                    technologyCenterList.get(i).setEvaluationLevel("不称职");
                    technologyCenterList.get(i).setPerformance("0.1");
                }
            }

            technologyCenterService.saveBatch(technologyCenterList);
            technologyCenterList.clear();
        }
    }
}
