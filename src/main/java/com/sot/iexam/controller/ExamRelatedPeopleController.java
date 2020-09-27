package com.sot.iexam.controller;

import com.sot.iexam.DO.*;
import com.sot.iexam.VO.examArrangeVo;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.exceptions.IExamException;
import com.sot.iexam.service.front.ExamRelatedPeopleService;
import com.sot.iexam.service.front.ExamService;
import com.sot.iexam.util.BeanUtils;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Kimbobo
 */
@Controller
@RequestMapping("examRelatedPeople")
public class ExamRelatedPeopleController {
    @Autowired
    ExamRelatedPeopleService examRelatedPeopleService;
    @Autowired
    ExamService examService;

    @LogAnnotation(needData = false, operateType = "获取考生安排信息列表")
    @RequestMapping("getExamArrangeInfo")
    public void getExamArrangeInfo(HttpServletResponse response, Integer examId) {
        List<examArrangeVo> examArrangeVoList = examRelatedPeopleService.getExamArrangeInfo(examId);
        ResponseUtil.ajaxReturn(response, examArrangeVoList, "null", 1);
    }

    @LogAnnotation(needData = false, operateType = "安排阅卷人")
    @RequestMapping("addReviewerExam")
    public void cancelReviewerExam(HttpServletResponse response, @RequestBody reviewerExam reviewerExam) {
        Map map = new HashMap(4);
        examRelatedPeopleService.addReviewerExam(reviewerExam, map);
        ResponseUtil.ajaxReturn(response, map, "阅卷安排成功", 1);
    }

    @LogAnnotation(needData = false, operateType = "取消阅卷人")
    @RequestMapping("cancelReviewerExam")
    public void cancelReviewerExam(HttpServletResponse response, Integer reviewerExamId,Integer examId) {
        Map map = new HashMap(4);
        examRelatedPeopleService.cancelReviewerExam(reviewerExamId,examId,map);
        ResponseUtil.ajaxReturn(response, map, "阅卷安排取消成功", 1);
    }

    @LogAnnotation(needData = false, operateType = "添加阅卷人的阅卷考场")
    @RequestMapping("addReviewerExamRoom")
    public void addReviewerExamRoom(HttpServletResponse response, @RequestBody reviewerExamRoom reviewerExamRoom) {
        examRelatedPeopleService.addReviewerExamRoom(reviewerExamRoom);
        ResponseUtil.ajaxReturn(response, "", "阅卷考场添加成功", 1);
    }

    @LogAnnotation(needData = false, operateType = "取消阅卷人的阅卷考场")
    @RequestMapping("cancelReviewerExamRoom")
    public void cancelReviewerExamRoom(HttpServletResponse response, Integer reviewerExamId, Integer examRoomId, Integer examId) {
        Map map = new HashMap(4);
        examRelatedPeopleService.cancelReviewerExamRoom(reviewerExamId, examRoomId, examId,map);
        ResponseUtil.ajaxReturn(response, map, "阅卷考场取消成功", 1);
    }

    @LogAnnotation(needData = false, operateType = "取消监考人对应考场")
    @RequestMapping("cancelInvigilatorExam")
    public void cancelInvigilatorExam(HttpServletResponse response, @RequestBody invigilatorExamRoom invigilatorExamRoom) {
        Map map = new HashMap(4);
        examRelatedPeopleService.cancelInvigilatorExam(invigilatorExamRoom, map);
        ResponseUtil.ajaxReturn(response, map, "监考信息取消成功", 1);
    }

    @LogAnnotation(needData = false, operateType = "安排对应考场的监考人")
    @RequestMapping("addInvigilatorExam")
    public void addInvigilatorExam(HttpServletResponse response, @RequestBody invigilatorExamRoom invigilatorExamRoom) {
        Map map = new HashMap(4);
        examRelatedPeopleService.addInvigilatorExamRoom(invigilatorExamRoom, map);
        ResponseUtil.ajaxReturn(response, map, "监考信息添加成功", 1);
    }

    /**
     * 获取监考安排的信息
     *
     * @param map //    {
     *            //        "page": 页号,
     *            //        "size": 大小,
     *            //        "invigilatorExamRoom": {
     *            //            {@link invigilatorExamRoom}
     *            //            "examRoomNumber":数字,
     *            //            "invigilatorId":数字,
     *            //            "examId":数字
     *            //        }
     *            //    }
     */
    @LogAnnotation(needData = false, operateType = "获取监考安排的信息列表")
    @RequestMapping("getInvigilatorExamList")
    @SuppressWarnings("unchecked")
    public void getInvigilatorExamList(HttpServletResponse response, @RequestBody Map map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        invigilatorExamRoom conditionsInObj = BeanUtils.map2Bean((LinkedHashMap) map.get("invigilatorExamRoom"), invigilatorExamRoom.class);
        Map m = examRelatedPeopleService.getInvigilatorExamList(page, size, conditionsInObj);
        ResponseUtil.ajaxReturn(response, m);
    }

    /**
     * @param map //    {
     *            //        "page": 页号,
     *            //        "size": 大小,
     *            //        "invigilatorExamRoom": {
     *            //            {@link invigilatorExamRoom}
     *            //
     *            //        }
     *            //    }
     * @throws Exception
     */
    @LogAnnotation(needData = false, operateType = "获取已经安排的监考人信息")
    @RequestMapping("getArrangedInvigilator")
    @SuppressWarnings("unchecked")
    public void getArrangedInvigilator(HttpServletResponse response, @RequestBody Map map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        invigilatorExamRoom conditionsInObj = BeanUtils.map2Bean((LinkedHashMap) map.get("invigilatorExamRoom"), invigilatorExamRoom.class);
        Map m = examRelatedPeopleService.getArrangedInvigilator(page, size, conditionsInObj);
        ResponseUtil.ajaxReturn(response, m);
    }

    @LogAnnotation(needData = false, operateType = "获取所有的监考人")
    @RequestMapping("getAllInvigilator")
    @SuppressWarnings("unchecked")
    public void getAllInvigilator(HttpServletResponse response, Integer examId, Integer page, Integer size) throws Exception {
        exam examInfo = examService.getExamInfo(examId);
        Map<String, Queue> m = examRelatedPeopleService.analyseReviewerAndInvigilators(examInfo);

        List list = new ArrayList();
        list.addAll(m.get("pureInvigilatorsId"));
        list.addAll(m.get("MixedIds"));
        Map peopleInfos = new HashMap();
        if (list.size() == 0) {
            peopleInfos.put("data", new ArrayList<>());
            peopleInfos.put("count", 0);
            ResponseUtil.ajaxReturn(response, peopleInfos);
            return;
        }
        peopleInfos = examRelatedPeopleService.getPeopleInfoPage(list, page, size);
        ResponseUtil.ajaxReturn(response, peopleInfos);
    }

    @LogAnnotation(needData = false, operateType = "获取阅卷人的信息")
    @RequestMapping("loadReviewersData")
    @SuppressWarnings("unchecked")
    public void loadReviewersData(HttpServletResponse response, Integer examId, Integer page, Integer size) throws Exception {
        exam examInfo = examService.getExamInfo(examId);
        Map<String, Queue> m = examRelatedPeopleService.analyseReviewerAndInvigilators(examInfo);

        List list = new ArrayList();
        list.addAll(m.get("pureReviewersId"));
        list.addAll(m.get("MixedIds"));
        Map peopleInfos = new HashMap();
        if (list.size() == 0) {
            peopleInfos.put("data", new ArrayList<>());
            peopleInfos.put("count", 0);
            ResponseUtil.ajaxReturn(response, peopleInfos);
            return;
        }
        peopleInfos = examRelatedPeopleService.getPeopleInfoPage(list, page, size);
        ResponseUtil.ajaxReturn(response, peopleInfos);
    }

    /**
     * 获取阅卷安排的信息（VO）
     *
     * @param map //    {
     *            //        "page": 页号,
     *            //        "size": 大小,
     *            //        "reviewerExam": {
     *            //            {@link reviewerExam}
     *            //            "examineesId":数字,
     *            //            "reviewerId":数字
     *            //        }
     *            //    }
     */
    @LogAnnotation(needData = false, operateType = "获取阅卷安排信息")
    @RequestMapping("getReviewerExamListVo")
    @SuppressWarnings("unchecked")
    public void getReviewerExamListVo(HttpServletResponse response, @RequestBody Map map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        reviewerExam conditionsInObj = BeanUtils.map2Bean((LinkedHashMap) map.get("reviewerExam"), reviewerExam.class);
        Map m = examRelatedPeopleService.getReviewerExamListVo(page, size, conditionsInObj);
        ResponseUtil.ajaxReturn(response, m);
    }


    /**
     * 重置监考
     *
     * @param response
     * @param examId
     */
    @LogAnnotation(needData = false, operateType = "重置监考")
    @RequestMapping("resetInvigilatorExam")
    public void resetInvigilatorExam(HttpServletResponse response, Integer examId) {
        String result = examRelatedPeopleService.resetInvigilator(examId);
        ResponseUtil.ajaxReturn(response, result, "监考安排重置成功", 1);
    }

    /**
     * 重置阅卷
     *
     * @param response
     * @param examId
     */
    @LogAnnotation(needData = false, operateType = "重置阅卷")
    @RequestMapping("resetReviewer")
    public void resetReviewer(HttpServletResponse response, Integer examId) {
        String result = examRelatedPeopleService.resetReviewer(examId);
        ResponseUtil.ajaxReturn(response, result, "阅卷安排重置成功", 1);
    }

    /**
     * 自动生成监考信息
     *
     * @param examId 考试id
     */
    @SuppressWarnings("unchecked")
    @LogAnnotation(needData = false, operateType = "自动生成监考信息")
    @RequestMapping("generateInvigilatorAutomatically")
    @Transactional(rollbackFor = Exception.class)
    public void generateInvigilatorAutomatically(HttpServletResponse response, Integer examId) {
        Map result = new HashMap();
        exam examInfo = examService.getExamInfo(examId);
        result.put("status", 0);
        try {
            if (!examService.checkStatus(examInfo, "canGenerate")) {
                return;
            }
            if (examInfo.getStatus().charAt(2) != '1') {
                throw new IExamException("没有生成考场之前不能生成监考信息");
            }
            Map<String, Queue> dataMap = examRelatedPeopleService.analyseReviewerAndInvigilators(examInfo);
            if (!examRelatedPeopleService.checkAndGenerateInvigilator(examInfo, dataMap)) {
                return;
            }
            result.put("info", "监考自动分配成功");
            result.put("status", 1);
            result.put("data", examInfo);
        } catch (Exception e) {
            result.put("errorMsg", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            ResponseUtil.ajaxReturn(response, result);
        }
    }

    /**
     * 自动生成阅卷信息
     *
     * @param examId 考试id
     */
    @SuppressWarnings("unchecked")
    @LogAnnotation(needData = false, operateType = "自动生成阅卷信息")
    @RequestMapping("generateReviewerAutomatically")
    @Transactional(rollbackFor = Exception.class)
    public void generateReviewerAutomatically(HttpServletResponse response, Integer examId) {
        Map result = new HashMap();
        exam examInfo = examService.getExamInfo(examId);
        result.put("status", 0);
        try {
            if (!examService.checkStatus(examInfo, "canGenerate")) {
                return;
            }
            Map<String, Queue> dataMap = examRelatedPeopleService.analyseReviewerAndInvigilators(examInfo);
            if (!examRelatedPeopleService.checkAndGenerateReviewer(examInfo, dataMap)) {
                return;
            }
            result.put("info", "阅卷自动分配成功");
            result.put("status", 1);
            result.put("data", examInfo);
        } catch (Exception e) {
            result.put("errorMsg", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            ResponseUtil.ajaxReturn(response, result);
        }
    }
}
    