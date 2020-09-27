package com.sot.iexam.controller;

import com.sot.iexam.DO.examNews;
import com.sot.iexam.DO.grade;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.front.GradeService;
import com.sot.iexam.util.BeanUtils;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kimbobo
 */
@Controller
@RequestMapping("grade")
public class GradeController {
    @Autowired
    GradeService gradeService;

    /**
     * 添加成绩
     *
     * @param grade JSON格式串，详细格式在这里{@link grade}
     */
    @LogAnnotation(needData = false, operateType = "添加成绩")
    @RequestMapping("addGrade")
    public void addGrade(HttpServletResponse response, @RequestBody grade grade) {
        gradeService.addGrade(grade);
        ResponseUtil.ajaxReturn(response, grade, "", 1);
    }

    /**
     * 修改成绩信息
     *
     * @param grade JSON格式串，详细格式在这里{@link grade}
     */
    @LogAnnotation(needData = false, operateType = "修改成绩信息")
    @RequestMapping("updateGrade")
    public void updateGrade(HttpServletResponse response, @RequestBody grade grade) {
        gradeService.updateGrade(grade);
        ResponseUtil.ajaxReturn(response, grade, "", 1);
    }

    /**
     * 获取成绩列表
     */
    @LogAnnotation(needData = false, operateType = "获取成绩列表")
    @RequestMapping("getGradeList")
    @SuppressWarnings("unchecked")
    public void addGrade(HttpServletResponse response, @RequestBody Map map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        grade conditionsInObj = BeanUtils.map2Bean((LinkedHashMap) map.get("grade"), grade.class);
        Map m = gradeService.getGradeList(page, size, conditionsInObj);
        ResponseUtil.ajaxReturn(response, m);
    }


    @LogAnnotation(needData = false, operateType = "******")
    @RequestMapping("getGradeListToSearch")
    @SuppressWarnings("unchecked")
    public void getGradeListToSearch(HttpServletResponse response, @RequestBody Map map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        grade conditionsInObj = BeanUtils.map2Bean((LinkedHashMap) map.get("grade"), grade.class);
        Map m = gradeService.getGradeListToSearch(page, size, conditionsInObj);
        ResponseUtil.ajaxReturn(response, m);
    }


    @LogAnnotation(needData = false, operateType = "获取考试成绩")
    @RequestMapping("getMyGradeList")
    @SuppressWarnings("unchecked")
    public void getMyGradeList(HttpServletResponse response, @RequestBody Map map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        grade conditionsInObj = BeanUtils.map2Bean((LinkedHashMap) map.get("grade"), grade.class);
        Map m = gradeService.getMyGradeList(page, size, conditionsInObj);
        ResponseUtil.ajaxReturn(response, m);
    }
}
    