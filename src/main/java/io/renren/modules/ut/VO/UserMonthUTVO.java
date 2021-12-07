package io.renren.modules.ut.VO;

import lombok.Data;

@Data
public class UserMonthUTVO {
    private String userName;
    private Double monthTime;

    public UserMonthUTVO() {}

    public UserMonthUTVO(String userName, Double monthTime) {
        this.userName = userName;
        this.monthTime = monthTime;
    }
}
