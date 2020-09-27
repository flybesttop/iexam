package com.sot.iexam.controller;

import com.sot.iexam.annotation.LogAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Kimbobo
 */
@Controller
@RequestMapping("door")
public class FrontController {

    @RequestMapping("login")
    public String login() {
        return "door-view/user_login";
    }

    @LogAnnotation(needData = false, operateType = "门户主页登录")
    @RequestMapping("index")
    public String index() {
        return "door-view/index";
    }

    @LogAnnotation(needData = false, operateType = "访问用户详细信息页面")
    @RequestMapping("userinfo")
    public String userinfo() {
        return "door-view/userinfo";
    }

    @LogAnnotation(needData = false, operateType = "访问当前的报名中的考试")
    @RequestMapping("allExams")
    public String allExams() {
        return "door-view/allExams";
    }

    @LogAnnotation(needData = false, operateType = "访问考试详情页面")
    @RequestMapping("exam_register")
    public String exam_register() {
        return "door-view/exam_register";
    }

    @LogAnnotation(needData = false, operateType = "访问所有用户自身的报名的但还未获取成绩的考试")
    @RequestMapping("myExams")
    public String myExams() {
        return "door-view/myExams";
    }

    @LogAnnotation(needData = false, operateType = "访问所有用户自身的报名的已经获取成绩的考试")
    @RequestMapping("myEndExams")
    public String myEndExams() {
        return "door-view/myEndExams";
    }

}
