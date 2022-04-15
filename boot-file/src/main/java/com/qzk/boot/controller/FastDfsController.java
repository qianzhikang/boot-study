package com.qzk.boot.controller;

import com.qzk.fastdfs.FastDFSClientUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Date 2022-04-14-21-17
 * @Author Courage
 */
@RestController
@RequestMapping(value = "fastdfs")
public class FastDfsController {
    @Resource
    private FastDFSClientUtil fastDFSClientUtil;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        String fileId;
        try {
            String originalFileName = file.getOriginalFilename();
            assert originalFileName != null;
            fileId = fastDFSClientUtil.uploadFile(file.getBytes(),originalFileName.substring(originalFileName.lastIndexOf(".")));
            return fastDFSClientUtil.getSourceUrl(fileId);
        } catch (Exception e) {
            System.err.println("文件上传失败");
            return "文件上传失败";
        }

    }

}
