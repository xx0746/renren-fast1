package io.renren.modules.ut.VO;

import lombok.Data;

import java.util.List;

@Data
public class WriteDayVO {
    private String year;
    private String month;
    private String day;
    private Long projectId;
    private List<UTWriteDayVO> dataList;
}
