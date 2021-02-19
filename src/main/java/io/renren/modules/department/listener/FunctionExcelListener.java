package io.renren.modules.department.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import io.renren.modules.department.entity.FunctionCenter;
import io.renren.modules.department.service.FunctionCenterService;

import java.util.ArrayList;
import java.util.List;

public class FunctionExcelListener extends AnalysisEventListener<FunctionCenter>{

    private FunctionCenterService functionCenterService;

    private String createTime = "";

    private List<FunctionCenter> functionCenterList = new ArrayList<>();

    public FunctionExcelListener(FunctionCenterService functionCenterService, String createTime){
        this.functionCenterService = functionCenterService;
        this.createTime = createTime;
    }

    @Override
    public void invoke(FunctionCenter functionCenter, AnalysisContext analysisContext) {
        if (functionCenter.getName() != null && functionCenter.getWordLoad() != null) {

            int workLoad = Integer.parseInt(functionCenter.getWordLoad());
            int knowledgeSkills = Integer.parseInt(functionCenter.getKnowledgeSkills());
            int communication = Integer.parseInt(functionCenter.getCommunicationCollaboration());
//            int jobResponse = Integer.parseInt(functionCenter.getJobResponsibility());
            int learningInnovation = Integer.parseInt(functionCenter.getLearningInnovation());

//            int workingDiscipline = Integer.parseInt(functionCenter.getWorkingDiscipline());
            functionCenter.setEvaluationResult(String.valueOf(workLoad + knowledgeSkills + communication  + learningInnovation));
            functionCenter.setCreateTime(createTime);
            functionCenterList.add(functionCenter);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (functionCenterList != null && functionCenterList.size() > 0) {

            functionCenterList.sort((o1, o2) -> {
                return Integer.parseInt(o2.getEvaluationResult()) - Integer.parseInt(o1.getEvaluationResult());
            });

            for (int i = 0; i < functionCenterList.size(); i++ ) {
                if (Integer.parseInt(functionCenterList.get(i).getEvaluationResult()) >= 90) {
                        functionCenterList.get(i).setEvaluationLevel("A");
//                        functionCenterList.get(i).setPerformance("0");
                } else if (Integer.parseInt(functionCenterList.get(i).getEvaluationResult()) >= 80) {
                        functionCenterList.get(i).setEvaluationLevel("B");
//                        functionCenterList.get(i).setPerformance("0");
                } else if (Integer.parseInt(functionCenterList.get(i).getEvaluationResult()) >= 70) {
                    functionCenterList.get(i).setEvaluationLevel("C");
//                    functionCenterList.get(i).setPerformance("0");
                } else if (Integer.parseInt(functionCenterList.get(i).getEvaluationResult()) >= 60) {
                    functionCenterList.get(i).setEvaluationLevel("C");
//                    functionCenterList.get(i).setPerformance("0");
                } else {
                    functionCenterList.get(i).setEvaluationLevel("C");
//                    functionCenterList.get(i).setPerformance("0");
                }
            }
            functionCenterService.saveBatch(functionCenterList);
            functionCenterList.clear();
        }
    }
}
