package io.renren.modules.department.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    //文件上传
    @PostMapping("/uploadFile")
    public void uploadFile(@RequestParam("file")MultipartFile file,String fileName) throws IOException {
        InputStream ips = file.getInputStream();
        File targetFile = new File("/home/excelFile/"+fileName+".xlsx");
        if (targetFile.exists()) {
            targetFile.delete();
        }
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        OutputStream ops = new FileOutputStream(targetFile);
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = ips.read(bytes)) != -1) {
            ops.write(bytes,0,len);
        }
        ips.close();
        ops.close();
    }

    //文件下载
    @GetMapping("/downloadFile")
    public void downloadFile(String fileName, HttpServletResponse response) throws IOException {
        File sourceFile = new File("/home/excelFile/"+fileName+".xlsx");
        if (!sourceFile.exists()) {
            throw new RuntimeException("需要下载的文件不存在");
        }
        InputStream ips = null;
        OutputStream ops = null;
        try {
            ips = new FileInputStream(sourceFile);
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String name = UUID.randomUUID().toString()+fileName+".xlsx";
            response.setHeader("Content-disposition","attachment;filename="+name);
            ops = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = ips.read(bytes)) != -1) {
                ops.write(bytes,0,len);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ops.close();
                ips.close();
            } catch (IOException e) {
                System.err.println("关闭流的时候出现IO异常");
            }
        }
    }
}
