package com.qzk.boot.controller;

import com.qzk.boot.utils.MinIoTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Description TODO
 * @Date 2022-04-14-16-59
 * @Author Courage
 */
@RestController
public class MinIOController {
    @Resource
    MinIoTemplate minIoTemplate;
    @PostMapping("/minio/upload")
    public String upload(MultipartFile file) throws Exception {
        String oldName = file.getOriginalFilename();
        assert oldName != null;
        String newName = UUID.randomUUID() + oldName.substring(oldName.lastIndexOf("."));
        minIoTemplate.putObject("upload",newName,file.getInputStream());
        return "http://124.221.233.62:9000/upload/" + newName;
    }
}
