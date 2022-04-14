package com.qzk.boot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Description TODO
 * @Date 2022-04-14-15-29
 * @Author Courage
 */
@RestController
public class FileController {
    @Value("${file.upload-path}")
    private String uploadPath;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
    @PostMapping("/upload")
    public String upload(MultipartFile file, HttpServletRequest request) throws IOException {
        String format = sdf.format(new Date());
        File folder = new File(uploadPath + format);
        if (!folder.isDirectory()){
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        assert  oldName != null;
        String newName = UUID.randomUUID() + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File(folder,newName));
        return request.getScheme() + "://" +request.getServerName() + ":" + request.getServerPort() + "/" + format + newName;
    }
    @GetMapping("/hello")
    public String test(){
        return "hello";
    }
}
