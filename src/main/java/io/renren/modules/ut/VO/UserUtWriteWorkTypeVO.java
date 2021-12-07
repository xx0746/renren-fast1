package io.renren.modules.ut.VO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserUtWriteWorkTypeVO {
    private String userName;
    private Double weekTime;
    private List<UTWriteVO> workTypeList = new ArrayList<>();
}
