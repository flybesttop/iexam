package com.sot.iexam.controller;

import com.sot.iexam.DO.*;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.VO.ticketVo;
import com.sot.iexam.enums.examStatus;
import com.sot.iexam.service.front.*;
import com.sot.iexam.util.BeanUtils;
import com.sot.iexam.util.ResponseUtil;
import com.zlzkj.core.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Kimbobo
 */

@Controller
@RequestMapping("exam")
public class ExamController {

    @Autowired
    ExamService examService;
    @Autowired
    ExamRelatedPeopleService examRelatedPeopleService;
    @Autowired
    ExamTicketService examTicketService;
    @Autowired
    RoomService roomService;

    @Autowired
    FileService fileService;
    @Autowired
    ExamFilesService examFilesService;
    @Autowired
    TitleService titleService;

    @LogAnnotation(needData = false, operateType = "获取对应考场的多有考生名单")
    @RequestMapping("getAllStdTicket")
    public String getAllStdTicket(Model model, Integer examRoomId) {

        Map ticketVoList = examService.getAllStdTicket(examRoomId);
        model.addAttribute("tickets", ticketVoList);
        return "exam_ticket_ListTable";
    }

    //查找考生的考试
    @LogAnnotation(needData = false, operateType = "根据用户id获取考试信息列表")
    @RequestMapping("getExamInfoListMyExams")
    @SuppressWarnings("unchecked")
    public void getExamInfoListMyExams(HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        Integer userId = BeanUtils.mapString2Integer(map.get("userId"));
        Map m = examService.getExamInfoListMyExams(page, size, userId);
        ResponseUtil.ajaxReturn(response, m);
    }

    @LogAnnotation(needData = false, operateType = "删除考试")
    @RequestMapping("deleteExam")
    public void deleteExam(HttpServletResponse response, @RequestParam("examId") Integer examId) {

        boolean check=examService.deleteExam(examId);

        ResponseUtil.ajaxReturn(response, "删除成功");
    }

    @LogAnnotation(needData = false, operateType = "获取准考证信息")
    @RequestMapping("getTicket")
    public String getTicket(Model model, @RequestParam("userId") Integer userId, @RequestParam("examId") Integer examId) {
        ticketVo ticketVo = examService.getTicket(userId, examId);
        model.addAttribute("ticket", ticketVo);
        return "door-view/exam_ticket";
    }

    @LogAnnotation(needData = false, operateType = "根据考试id获取考试的详细信息")
    @RequestMapping("exam_register")
    public String exam_register(Model model, @RequestParam("examId") Integer examId) {
        exam exam = examService.getExamInfo(examId);
        title title = titleService.getTitleByTitleId(exam.getTitleId());
        title.setPhotoPath(UploadUtils.parseFileUrl(title.getPhotoPath()));
        model.addAttribute("exam", exam);
        model.addAttribute("title", title);
        examFiles examFiles = new examFiles();
        examFiles.setExamId(examId);
        Map map = examFilesService.getFiles(0, 1000, examFiles);
        model.addAttribute("map", map);
        return "door-view/exam_register";
    }


    /**
     * 根据考生的id,获取考试的考试信息列表（这个考试的信息是已经生成了准考证的信息）
     * 对于没有生成准考证的信息，一律为报名信息 {@link RegistrationController#getRegistrationByExamineesId}
     * 返回的实体集合有
     * 1、准考证信息 {@link examTicket}
     * 2、考场信息 {@link examRoom}
     * 3、考试信息 {@link exam}
     *
     * @param map //    {
     *            //        "page": 页号,
     *            //        "size": 大小,
     *            //        "examTicket": {
     *            //            {@link examTicket}
     *            //            "examRoomNumber":数字,
     *            //            "siteNumber":数字,
     *            //            "examineesId":数字,
     *            //            "examId":数字
     *            //        }
     *            //    }
     */
    @LogAnnotation(needData = false, operateType = "根据准考证信息获取考试信息列表")
    @RequestMapping("getExamList")
    @SuppressWarnings("unchecked")
    public void getExamList(HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        examTicket conditionsInObj = BeanUtils.map2Bean((LinkedHashMap) map.get("examTicket"), examTicket.class);
        Map m = examService.getExamVoList(page, size, conditionsInObj);
        ResponseUtil.ajaxReturn(response, m);
    }

    /**
     * 获取考试信息分页
     * 页数从1开始，不传值默认第一页 大小10
     *
     * @param map //    {
     *            //        "page": 页号,
     *            //        "size": 大小,
     *            //        "exam": {
     *            //            {@link exam}
     *            //        }
     *            //    }
     */
    @LogAnnotation(needData = false, operateType = "根据考试信息获取考试信息列表")
    @RequestMapping("getExamInfoList")
    @SuppressWarnings("unchecked")
    public void getExamInfoList(HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        exam e = BeanUtils.map2Bean((LinkedHashMap) map.get("exam"), exam.class);
        Map m = examService.getExamInfoList(page, size, e);
        ResponseUtil.ajaxReturn(response, m);
    }

    @LogAnnotation(needData = false, operateType = "获取阅卷对应的考试")
    @RequestMapping("getExamInfoListToReviewer")
    @SuppressWarnings("unchecked")
    public void getExamInfoListToReviewer(HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        Integer userId = BeanUtils.mapString2Integer(map.get("userId"));
        String search = (String) map.get("search");
        Map m = examService.getExamInfoListToReviewer(page, size, search, userId);
        ResponseUtil.ajaxReturn(response, m);
    }

    @LogAnnotation(needData = false, operateType = "获取刚发布但还没有开始报名的所有考试")
    @RequestMapping("getExamInfoListToPublish")
    public void getExamInfoListToPublish(HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        String search = (String) map.get("search");
        Map m = examService.getExamInfoListToPublish(page, size, search);
        ResponseUtil.ajaxReturn(response, m);
    }

    @LogAnnotation(needData = false, operateType = "获取带有职称的考试信息列表")
    @RequestMapping("getExamInfoListWithTitle")
    @SuppressWarnings("unchecked")
    public void getExamInfoListWithTitle(HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        exam e = BeanUtils.map2Bean((LinkedHashMap) map.get("exam"), exam.class);
        Map m = examService.getExamInfoListWithTitle(page, size, e);
        ResponseUtil.ajaxReturn(response, m);
    }


    @LogAnnotation(needData = false, operateType = "上传考试文件信息")
    @RequestMapping("uploadExamFile")
    public void uploadExamFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] files, @RequestParam("fileTitle") String[] fileTitle, @RequestParam("examId") Integer examId) {
        Map<String, Object> data = new HashMap<>(8);

        for (int i = 0; i < files.length; i++) {
            boolean flag = fileService.uploadMultipartFile(data, files[i]);
            if (flag) {//上传成功
                ResponseUtil.ajaxReturn(response, data, "上传完毕", 1);
                examFiles examFiles = new examFiles();
                examFiles.setFilePath(data.get("imgUrl").toString());
                examFiles.setExamId(examId);
                examFiles.setFileTitle(fileTitle[i]);
                examFilesService.insert(examFiles);
            } else {
                ResponseUtil.ajaxReturn(response, null, data.get("errorMessage").toString(), 0);
            }
        }
    }


    /**
     * 添加考试信息
     *
     * @param exam exam的JSON格式串，详细格式在这里{@link exam}
     */
    @LogAnnotation(needData = false, operateType = "添加考试信息")
    @RequestMapping("addExam")
    public void addExam(HttpServletResponse response, @RequestBody exam exam) {
        examService.addExam(exam);
        ResponseUtil.ajaxReturn(response, exam, "", 1);
    }

    @RequestMapping("updateExam")
    @LogAnnotation(needData = false, operateType = "修改考试信息")
    public void updateExam(HttpServletResponse response, @RequestBody exam exam) {
        examService.updateExam(exam);
        ResponseUtil.ajaxReturn(response, exam, "", 1);
    }

    @RequestMapping("updateExam2")
    @LogAnnotation(needData = false, operateType = "修改考试信息并且激活计时器")
    public void updateExam2(HttpServletResponse response, @RequestBody exam exam) {
        examService.updateExamAndScheduler(exam);
        ResponseUtil.ajaxReturn(response, exam, "", 1);
    }

    /**
     * 根据id查找考试信息
     *
     * @param examId 需要查找的考试的信息
     */
    @LogAnnotation(needData = false, operateType = "根据id查找考试信息")
    @RequestMapping("getExamInfoById")
    public void getExamInfoById(HttpServletResponse response, Integer examId) {
        exam examInfo = examService.getExamInfo(examId);
        ResponseUtil.ajaxReturn(response, examInfo);
    }


    /**
     * 自动生成所有考试信息（考场座位号这些）
     *
     * @param examId 需要自动生成的考试id
     */
    @SuppressWarnings("unchecked")
    @LogAnnotation(needData = false, operateType = "自动生成所有考试信息")
    @RequestMapping("generateAllExamInfoAutomatically")
    @Transactional(rollbackFor = Exception.class)
    public void generateAllExamInfoAutomatically(HttpServletResponse response, Integer examId) {
        Map result = new HashMap(8);

        exam examInfo = examService.getExamInfo(examId);

        try {
            if (!examService.checkStatus(examInfo, "canGenerate")) {
                return;
            }
            if (!roomService.checkAndGenerateExamRoom(examInfo)) {
                result.put("info", "已经生成过了");
            }

            Map<String, Queue> dataMap = examRelatedPeopleService.analyseReviewerAndInvigilators(examInfo);
            if (!examRelatedPeopleService.checkAndGenerateInvigilator(examInfo, dataMap)) {
                return;
            }

            if (!examRelatedPeopleService.checkAndGenerateReviewer(examInfo, dataMap)) {
                return;
            }
            //全部分配完毕 修改考试的信息
            result.put("info", "自动生成完毕");
            result.put("status", 1);
            result.put("data", examInfo);
            examInfo.setStatus(examStatus.GENERATE_FINISH.getCode());
            examService.updateStatus(examInfo);

        } catch (Exception e) {
            result.put("status", 0);
            e.printStackTrace();
            result.put("errorMsg", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            ResponseUtil.ajaxReturn(response, result);
        }

    }

}
    