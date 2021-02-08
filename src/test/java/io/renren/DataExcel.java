package io.renren;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DataExcel {
    @ExcelProperty(value = "用户名",index = 0)
    private String name;
    @ExcelProperty(value = "密码",index = 1)
    private String password;

    public DataExcel(String name,String password){
        this.name = name;
        this.password = password;
    }
}
