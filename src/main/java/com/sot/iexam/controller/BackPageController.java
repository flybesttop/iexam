package com.sot.iexam.controller;

import com.sot.iexam.annotation.LogAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Kimbobo
 */
@Controller
@RequestMapping("backPage")
public class BackPageController {

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @LogAnnotation(needData = false, operateType = "登录主页")
    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @LogAnnotation(needData = false, operateType = "查看权限")
    @RequestMapping("back_nodes")
    public String backNodes() {
        return "back_nodes";
    }

    @LogAnnotation(needData = false, operateType = "访问后台角色信息页面")
    @RequestMapping("back_roles")
    public String backRoles() {
        return "back_roles";
    }

    @LogAnnotation(needData = false, operateType = "访问后台用户信息页面")
    @RequestMapping("back_users")
    public String back_users() {
        return "back_users";
    }

    @LogAnnotation(needData = false, operateType = "访问考试安排发布页面")
    @RequestMapping("exam_publish")
    public String exam_publish() {
        return "exam_publish";
    }

    @LogAnnotation(needData = false, operateType = "访问考试安排信息页面")
    @RequestMapping("exam_arrange")
    public String exam_arrange() {
        return "exam_arrange";
    }

    @LogAnnotation(needData = false, operateType = "访问阅卷管理页面")
    @RequestMapping("exam_review")
    public String exam_review() {
        return "exam_review";
    }

    @LogAnnotation(needData = false, operateType = "访问监考管理页面")
    @RequestMapping("exam_invigilator")
    public String exam_invigilator() {
        return "exam_invigilator";
    }

    @LogAnnotation(needData = false, operateType = "访问考试职称页面")
    @RequestMapping("exam_title")
    public String exam_title() {
        return "exam_title";
    }

    @LogAnnotation(needData = false, operateType = "访问阅卷职称信信息页面")
    @RequestMapping("review_title")
    public String review_title() {
        return "review_title";
    }

    @LogAnnotation(needData = false, operateType = "访问新闻管理页面")
    @RequestMapping("informations_edit")
    public String informations_edit() {
        return "informations_edit";
    }

    @LogAnnotation(needData = false, operateType = "访问考务中心新闻管理页面")
    @RequestMapping("back_informations_edit")
    public String back_informations_edit() {
        return "back_informations_edit";
    }

    @LogAnnotation(needData = false, operateType = "查看后台系统日志")
    @RequestMapping("logBack")
    public String logBack() {
        return "logBack";
    }

    @LogAnnotation(needData = false, operateType = "查看前台系统日志")
    @RequestMapping("logDoor")
    public String logDoor() {
        return "logDoor";
    }

    @LogAnnotation(needData = false, operateType = "访问异常成绩处理页面")
    @RequestMapping("controversialGrade")
    public String controversialGrade() {
        return "controversialGrade";
    }

}

