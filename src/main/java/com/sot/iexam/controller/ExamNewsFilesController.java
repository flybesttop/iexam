package com.sot.iexam.controller;

import com.sot.iexam.DO.newsFiles;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.front.FileService;
import com.sot.iexam.service.front.NewsFilesService;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("examNewsFiles")
public class ExamNewsFilesController {
    @Autowired
    FileService fileService;
    @Autowired
    NewsFilesService newsFilesService;

    @LogAnnotation(needData = false, operateType = "上传新闻文件")
    @RequestMapping("uploadNewsFile")
    public void uploadNewsFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] files, @RequestParam("fileTitle") String[] fileTitle, @RequestParam("newsId") Integer newsId) {
        Map<String, Object> data = new HashMap<>(8);

        for (int i = 0; i < files.length; i++) {
            boolean flag = fileService.uploadMultipartFile(data, files[i]);
            if (flag) {//上传成功
                ResponseUtil.ajaxReturn(response, data, "上传完毕", 1);
                newsFiles newsFiles = new newsFiles();
                newsFiles.setNewsId(newsId);
                newsFiles.setFilePath(data.get("imgUrl").toString());
                newsFiles.setFileTitle(fileTitle[i]);
                newsFilesService.insert(newsFiles);
            } else {
                ResponseUtil.ajaxReturn(response, null, data.get("errorMessage").toString(), 0);
            }
        }
    }

    @LogAnnotation(needData = false, operateType = "获取新闻文件")
    @RequestMapping("getNewsFiles")
    public void getFiles(HttpServletResponse response, Integer newsId) {
        List<newsFiles> files = newsFilesService.getFilesByNewsId(newsId);
        ResponseUtil.ajaxReturn(response, files, "", 1);
    }

}
    