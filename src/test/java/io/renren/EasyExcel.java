package io.renren;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class EasyExcel {
    @Test
    public void test() throws IOException {
        File file = new File("C:\\Users\\Administrator\\Desktop\\excel\\test.xlsx");
        if (!file.exists()) {
            file.createNewFile();
        }

        OutputStream outputStream = new FileOutputStream(file);

        List<DataExcel> list = new ArrayList<>();
        list.add(new DataExcel("root1","1013"));
        list.add(new DataExcel("root2","1013"));
        list.add(new DataExcel("root3","1013"));
        list.add(new DataExcel("root4","1013"));

        com.alibaba.excel.EasyExcel.write(outputStream,DataExcel.class).sheet("模板").doWrite(list);
    }
}
