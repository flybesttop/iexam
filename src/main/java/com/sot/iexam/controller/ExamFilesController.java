package com.sot.iexam.controller;

import com.sot.iexam.DO.examFiles;
import com.sot.iexam.DO.examinees;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.front.ExamFilesService;
import com.sot.iexam.util.BeanUtils;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("examFiles")
public class ExamFilesController {
    @Autowired
    ExamFilesService examFilesService;


    /**
     * 获取考试文件信息（基本用来根据考试id获取文件）
     * 页数从1开始，不传值默认第一页 大小10
     * // {
     * //  "page":页号,
     * //  "size":页大小,
     * //  "examFiles":{
     * //       {@link examFiles}
     * //      }
     * //  }

     */
    @LogAnnotation(needData = false, operateType = "获取考试文件信息")
    @RequestMapping("getFilesList")
    @SuppressWarnings("unchecked")
    public void getFiles(HttpServletResponse response,@RequestBody Map<String, Object> map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        examFiles conditionsInObj = BeanUtils.map2Bean((LinkedHashMap) map.get("examNews"), examFiles.class);
        Map m = examFilesService.getFiles(page,size,conditionsInObj);
        ResponseUtil.ajaxReturn(response, m);
    }
}
    