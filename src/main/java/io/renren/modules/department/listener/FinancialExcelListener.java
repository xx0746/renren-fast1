package io.renren.modules.department.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.department.entity.DesignCenter;
import io.renren.modules.department.entity.FinancialCenter;
import io.renren.modules.department.service.FinancialCenterService;

import java.util.ArrayList;
import java.util.List;

public class FinancialExcelListener extends AnalysisEventListener<FinancialCenter> {

    private FinancialCenterService financialCenterService;

    private List<FinancialCenter> financialCenterList = new ArrayList<>();

    private String createTime = "";

    public FinancialExcelListener(FinancialCenterService financialCenterService,String createTime){
        this.financialCenterService = financialCenterService;
        this.createTime = createTime;
    }

    @Override
    public void invoke(FinancialCenter data, AnalysisContext context) {
        if (data.getName() != null && data.getWordLoad() != null) {

            int workLoad = Integer.parseInt(data.getWordLoad());
            int knowledgeSkills = Integer.parseInt(data.getKnowledgeSkills());
            int communication = Integer.parseInt(data.getCommunicationCollaboration());
            int jobResponse = Integer.parseInt(data.getJobResponsibility());
            int learningInnovation = Integer.parseInt(data.getLearningInnovation());
            int workingDiscipline = Integer.parseInt(data.getWorkingDiscipline());
            int progressController = Integer.parseInt(data.getProgressController());
            int resultValue = Integer.parseInt(data.getResultValue());


            data.setEvaluationResult(String.valueOf(workLoad + knowledgeSkills + communication + jobResponse + learningInnovation + workingDiscipline + progressController + resultValue));
            data.setCreateTime(createTime);
            financialCenterList.add(data);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (financialCenterList != null && financialCenterList.size() > 0) {

            financialCenterList.sort((o1, o2) -> {
                return Integer.parseInt(o2.getEvaluationResult()) - Integer.parseInt(o1.getEvaluationResult());
            });

            for (int i = 0; i < financialCenterList.size(); i++ ) {
                if (Integer.parseInt(financialCenterList.get(i).getEvaluationResult()) >= 90) {
                    financialCenterList.get(i).setEvaluationLevel("优秀");
                    financialCenterList.get(i).setPerformance("1.3");
                } else if (Integer.parseInt(financialCenterList.get(i).getEvaluationResult()) >= 80) {
                    financialCenterList.get(i).setEvaluationLevel("良好");
                    financialCenterList.get(i).setPerformance("1.1");
                } else if (Integer.parseInt(financialCenterList.get(i).getEvaluationResult()) >= 70) {
                    financialCenterList.get(i).setEvaluationLevel("称职");
                    financialCenterList.get(i).setPerformance("1.0");
                } else if (Integer.parseInt(financialCenterList.get(i).getEvaluationResult()) >= 60) {
                    financialCenterList.get(i).setEvaluationLevel("基本称职");
                    financialCenterList.get(i).setPerformance("0.8");
                } else {
                    financialCenterList.get(i).setEvaluationLevel("不称职");
                    financialCenterList.get(i).setPerformance("0.1");
                }
            }

            financialCenterService.saveBatch(financialCenterList);
            financialCenterList.clear();
        }
    }
}
