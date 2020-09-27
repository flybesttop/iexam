package com.sot.iexam.controller;

import com.sot.iexam.DO.exam;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.front.ExamService;
import com.sot.iexam.service.front.ExamTicketService;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kimbobo
 */
@Controller
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    ExamService examService;
    @Autowired
    ExamTicketService examTicketService;

    /**
     * 自动生成准考证信息
     *
     * @param examId 考试id
     */
    @SuppressWarnings("unchecked")
    @LogAnnotation(needData = false, operateType = "自动生成准考证信息")
    @RequestMapping("generateTicketsAutomatically")
    @Transactional(rollbackFor = Exception.class)
    public void generateTicketsAutomatically(HttpServletResponse response, Integer examId) {
        Map result = new HashMap();
        exam examInfo = examService.getExamInfo(examId);
        result.put("status", 0);
        try {
            if (!examService.checkStatus(examInfo, "canGenerate")) {
                return;
            }
            if (!examTicketService.generateTickets(examInfo)) {
                return;
            }
            result.put("info", "准考证自动分配完毕");
            result.put("status", 1);
        } catch (Exception e) {
            result.put("errorMsg", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            ResponseUtil.ajaxReturn(response, result);
        }
    }
}
    