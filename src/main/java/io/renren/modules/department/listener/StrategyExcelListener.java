package io.renren.modules.department.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.department.entity.DesignCenter;
import io.renren.modules.department.entity.QualityTestingCenter;
import io.renren.modules.department.entity.StrategyCenter;
import io.renren.modules.department.service.QualityTestingCenterService;
import io.renren.modules.department.service.StrategyCenterService;

import java.util.ArrayList;
import java.util.List;

public class StrategyExcelListener extends AnalysisEventListener<StrategyCenter> {
    private StrategyCenterService strategyCenterService;

    private List<StrategyCenter> strategyCenterList = new ArrayList<>();

    private String createTime = "";

    public StrategyExcelListener(StrategyCenterService strategyCenterService,String createTime){
        this.strategyCenterService = strategyCenterService;
        this.createTime = createTime;
    }

    @Override
    public void invoke(StrategyCenter data, AnalysisContext context) {
        //System.out.println(data);
        if (data.getName() != null && data.getWordLoad() != null) {

            int workLoad = Integer.parseInt(data.getWordLoad());
            int knowledgeSkills = Integer.parseInt(data.getKnowledgeSkills());
            int communication = Integer.parseInt(data.getCommunicationCollaboration());
            int jobResponse = Integer.parseInt(data.getJobResponsibility());
            int learningInnovation = Integer.parseInt(data.getLearningInnovation());
            int workingDiscipline = Integer.parseInt(data.getWorkingDiscipline());
            int progressController = Integer.parseInt(data.getProgressController());
            int resultValue = Integer.parseInt(data.getResultValue());
            int investigate = Integer.parseInt(data.getInvestigate());


            data.setEvaluationResult(String.valueOf(workLoad + knowledgeSkills + communication + jobResponse + learningInnovation + workingDiscipline + progressController + resultValue + investigate));
            data.setCreateTime(createTime);
            strategyCenterList.add(data);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (strategyCenterList != null && strategyCenterList.size() > 0) {

            strategyCenterList.sort((o1, o2) -> {
                return Integer.parseInt(o2.getEvaluationResult()) - Integer.parseInt(o1.getEvaluationResult());
            });

            for (int i = 0; i < strategyCenterList.size(); i++ ) {
                if (Integer.parseInt(strategyCenterList.get(i).getEvaluationResult()) >= 90) {
                    strategyCenterList.get(i).setEvaluationLevel("优秀");
                    strategyCenterList.get(i).setPerformance("1.3");
                } else if (Integer.parseInt(strategyCenterList.get(i).getEvaluationResult()) >= 80) {
                    strategyCenterList.get(i).setEvaluationLevel("良好");
                    strategyCenterList.get(i).setPerformance("1.1");
                } else if (Integer.parseInt(strategyCenterList.get(i).getEvaluationResult()) >= 70) {
                    strategyCenterList.get(i).setEvaluationLevel("称职");
                    strategyCenterList.get(i).setPerformance("1.0");
                } else if (Integer.parseInt(strategyCenterList.get(i).getEvaluationResult()) >= 60) {
                    strategyCenterList.get(i).setEvaluationLevel("基本称职");
                    strategyCenterList.get(i).setPerformance("0.8");
                } else {
                    strategyCenterList.get(i).setEvaluationLevel("不称职");
                    strategyCenterList.get(i).setPerformance("0.1");
                }
            }

            strategyCenterService.saveBatch(strategyCenterList);
            strategyCenterList.clear();
        }
    }
}
