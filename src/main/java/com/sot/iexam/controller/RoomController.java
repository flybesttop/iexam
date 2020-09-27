package com.sot.iexam.controller;

import com.sot.iexam.DAO.mybatis.mapper.invigilatorExamRoomMapper;
import com.sot.iexam.DAO.mybatis.mapper.invigilatorMapper;
import com.sot.iexam.DAO.mybatis.mapper.reviewerExamRoomMapper;
import com.sot.iexam.DO.classRoom;
import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.examRoom;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.front.ExamRelatedPeopleService;
import com.sot.iexam.service.front.ExamService;
import com.sot.iexam.service.front.RoomService;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kimbobo
 */

@Controller
@RequestMapping("room")
public class RoomController {
    @Autowired
    RoomService roomService;
    @Autowired
    ExamService examService;
    @Autowired
    invigilatorExamRoomMapper invigilatorExamRoomMapper;
    @Autowired
    reviewerExamRoomMapper reviewerExamRoomMapper;


    @LogAnnotation(needData = false, operateType = "重置考场")
    @RequestMapping("resetExamRoom")
    public void resetExamRoom(HttpServletResponse response, Integer examId) {
        String result = roomService.resetExamRoom(examId);
        ResponseUtil.ajaxReturn(response, result, "考场安排重置完毕", 1);
    }


    @LogAnnotation(needData = false, operateType = "根据考试id获取考试信息")
    @RequestMapping("getExamRoomInfoByExamId")
    public void getExamRoomInfoByExamId(HttpServletResponse response, Integer examId) {
        List<examRoom> examRooms = roomService.getExamRoomInfoByExamId(examId);
        ResponseUtil.ajaxReturn(response, examRooms);
    }

    @LogAnnotation(needData = false, operateType = "获取所有可用的考场")
    @RequestMapping("getAllClassRoomCanUse")
    public void getAllClassRoomCanUse(HttpServletResponse response, String startTime, String endTime,Integer page,Integer size,String room_search) {

        Map m=roomService.getAllClassRoomCanUse(startTime, endTime,page,size,room_search);
//        List<classRoom> allClassRoomCanUse = roomService.getAllClassRoomCanUse(startTime, endTime,page,size,room_search);
        ResponseUtil.ajaxReturn(response, m);
    }

    @LogAnnotation(needData = false, operateType = "添加考场安排")
    @RequestMapping("addArrangement")
    public void addArrangement(HttpServletResponse response, @RequestBody examRoom examRoom) {
        roomService.addArrangement(examRoom);
        ResponseUtil.ajaxReturn(response, examRoom, "", 1);
    }

    @LogAnnotation(needData = false, operateType = "取消考场安排")
    @RequestMapping("cancelArrangement")
    public void cancelArrangement(HttpServletResponse response, Integer examRoomId,Integer examId) {
        invigilatorExamRoomMapper.deleteByExamRoomId(examRoomId);
        reviewerExamRoomMapper.deleteByExamRoomId(examRoomId);
        roomService.deleteByExamRoomId(examRoomId,examId);
        ResponseUtil.ajaxReturn(response, null, "", 1);
    }

    @LogAnnotation(needData = false, operateType = "修改考场信息")
    @RequestMapping("updateExamRoom")
    public void updateExamRoom(HttpServletResponse response, @RequestBody examRoom examRoom) {

        roomService.updateExamRoom(examRoom);
        ResponseUtil.ajaxReturn(response, null, "", 1);
    }


    /**
     * 自动生成考场信息
     *
     * @param examId 考试id
     */
    @SuppressWarnings("unchecked")
    @LogAnnotation(needData = false, operateType = "自动生成考场信息")
    @RequestMapping("generateClassRoomAutomatically")
    @Transactional(rollbackFor = Exception.class)
    public void generateClassRoomAutomatically(HttpServletResponse response, Integer examId) {
        Map result = new HashMap();
        exam examInfo = examService.getExamInfo(examId);
        result.put("status", 0);
        try {
            if (!examService.checkStatus(examInfo, "canGenerate")) {
                return;
            }
            if (!roomService.checkAndGenerateExamRoom(examInfo)) {
                result.put("info", "已经生成过了");
            }

            result.put("info", "考场自动分配完毕");
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
     * 获取所有考场的信息
     */


}
    