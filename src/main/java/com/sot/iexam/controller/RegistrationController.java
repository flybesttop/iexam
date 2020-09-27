package com.sot.iexam.controller;

import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.registration;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.front.RegistrationService;
import com.sot.iexam.util.BeanUtils;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kimbobo
 */
@RestController
@RequestMapping("registration")
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;

    /**
     * 根据id删除报名
     *
     * @param registration
     */
    @LogAnnotation(needData = false, operateType = "取消报名")
    @RequestMapping("deleteByRegistrationId")
    public void deleteByRegistrationId(HttpServletResponse response, @RequestBody registration registration) {
        Map result = new HashMap();
        boolean r = registrationService.deleteByRegistrationId(registration, result);
        ResponseUtil.ajaxReturn(response, result, "", r ? 1 : 0);
    }

    /**
     * 添加报名信息 格式：{@link registration}
     *
     * @param registration 传入的考生JSON串
     */
    @LogAnnotation(needData = false, operateType = "添加报名")
    @RequestMapping("addRegistration")
    public void addRegistration(HttpServletResponse response, @RequestBody registration registration) {
        Map result = new HashMap();
        boolean r = registrationService.addRegistration(registration, result);
        ResponseUtil.ajaxReturn(response, result, "", r ? 1 : 0);
    }

    @LogAnnotation(needData = false, operateType = "修改报名信息")
    @RequestMapping("updateRegistration")
    public void updateRegistration(HttpServletResponse response, @RequestBody registration registration) {
        Map result = new HashMap();
        boolean r = registrationService.updateRegistration(registration, result);
        ResponseUtil.ajaxReturn(response, null, "", r ? 1 : 0);
    }

    /**
     * 根据考生id 查找考生的报名科目
     * 没有生成准考证的信息都为报名信息
     * 生成准考证的信息为考试信息 {@link ExamController#getExamInfoList(HttpServletResponse, Map)}
     *
     * @param examineesId 考生id
     *                    返回的map里面存放的是考试{@link exam}的信息
     */
    @LogAnnotation(needData = false, operateType = "根据考生id查看考生报名的科目")
    @RequestMapping("getRegistrationByExamineesId")
    public void getRegistrationByExamineesId(HttpServletResponse response, Integer examineesId, Integer page, Integer size) {
        Map m = registrationService.getRegistrationByExamineesId(examineesId, page, size);
        ResponseUtil.ajaxReturn(response, m);
    }

    /**
     * 根据考试id 查看对应报名的学生信息
     *
     * @param examId 考试id
     */
    @LogAnnotation(needData = false, operateType = "根据考生id查看报名的考生")
    @RequestMapping("getRegistrationByExamId")
    public void getRegistrationByExamId(HttpServletResponse response, Integer examId, Integer page, Integer size) {
        Map m = registrationService.getRegistrationByExamId(examId, page, size);
        ResponseUtil.ajaxReturn(response, m);
    }

    /**
     * 根据id查找 报名信息，实际没有意义
     *
     * @param registrationId 需要查找的报名考生的id
     */
    @LogAnnotation(needData = false, operateType = "根据报名id查看报名信息")
    @RequestMapping("getRegistrationById")
    public void getRegistrationById(HttpServletResponse response, Integer registrationId) {
        registration registration = registrationService.getRegistrationById(registrationId);
        ResponseUtil.ajaxReturn(response, registration);
    }

    /**
     * 获取报名信息列表（没什么实际意义）
     * 页数从1开始，不传值默认第一页 大小10
     *
     * @param map //    {
     *            //        "page": 页号,
     *            //        "size": 大小,
     *            //        "registration": {
     *            //            {@link registration}
     *            //        }
     *            //    }
     */
    @LogAnnotation(needData = false, operateType = "获取报名信息列表")
    @RequestMapping("getRegistrationList")
    @SuppressWarnings("unchecked")
    public void getRegistrationList(HttpServletResponse response, @RequestBody Map map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        registration conditionsInObj = BeanUtils.map2Bean((LinkedHashMap) map.get("registration"), registration.class);
        Map m = registrationService.getRegistrationList(page, size, conditionsInObj);
        ResponseUtil.ajaxReturn(response, m);
    }
}
    