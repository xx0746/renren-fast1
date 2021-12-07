package io.renren.modules.ut.VO;

import lombok.Data;

import java.util.List;

@Data
public class WriteVO {
    private String year;
    private String month;
    private String week;
    private Long projectId;
    private List<UTWriteVO> dataList;
}
