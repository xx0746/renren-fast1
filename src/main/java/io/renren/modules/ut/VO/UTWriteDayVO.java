package io.renren.modules.ut.VO;

import lombok.Data;

@Data
public class UTWriteDayVO {
    private Long oneId;
    private String oneName;
    private Long twoId;
    private String twoName;
    private Double ut;
    private Integer status;
    public UTWriteDayVO() {

    }

    public UTWriteDayVO(Long oneId, Long twoId) {
        this.oneId = oneId;
        this.twoId = twoId;
    }
}
