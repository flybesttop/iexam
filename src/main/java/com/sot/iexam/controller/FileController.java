package com.sot.iexam.controller;


import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.examFiles;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.front.ExamFilesService;
import com.sot.iexam.service.front.ExamService;
import com.sot.iexam.service.front.FileService;
import com.sot.iexam.util.ResponseUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kimbobo
 */
@Controller
@RequestMapping("file")
public class FileController {
    @Autowired
    FileService fileService;
    @Autowired
    ExamFilesService examFilesService;

    @LogAnnotation(needData = false, operateType = "上传文件")
    @RequestMapping("uploadFile")
    public void uploadImg(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) {
        Map<String, Object> data = new HashMap<>(8);
        boolean flag = fileService.uploadMultipartFile(data, file);
        //上传成功
        if (flag) {
            ResponseUtil.ajaxReturn(response, data, "上传完毕", 1);
        } else {
            ResponseUtil.ajaxReturn(response, null, data.get("errorMessage").toString(), 0);
        }

    }

}
    