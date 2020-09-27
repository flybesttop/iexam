package com.sot.iexam.scheduleJob;

import com.sot.iexam.DAO.mybatis.mapper.examMapper;
import com.sot.iexam.DO.exam;
import com.sot.iexam.enums.examStatus;
import com.sot.iexam.service.front.ExamRelatedPeopleService;
import com.sot.iexam.service.front.ExamService;
import com.sot.iexam.service.front.ExamTicketService;
import com.sot.iexam.service.front.RoomService;
import com.sot.iexam.util.ResponseUtil;
import com.sot.iexam.util.Timmer;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Map;
import java.util.Queue;

public class examStatusChangeJob extends QuartzJobBean {
    @Autowired
    examMapper examMapper;
    @Autowired
    ExamService examService;
    @Autowired
    RoomService roomService;
    @Autowired
    ExamRelatedPeopleService examRelatedPeopleService;
    @Autowired
    ExamTicketService examTicketService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();


        Integer examId = (Integer) jobDataMap.get("examId");
        String status = (String) jobDataMap.get("status");
        System.out.println(Timmer.now() + "---" + examId + "-----" + status);

        System.out.println("exam---to -->" + status);
        exam examInfo = examMapper.getExamById(examId);
        if (examStatus.GENERATED.getCode().equals(status) && !examStatus.GENERATE_FINISH.getCode().equals(examInfo.getStatus())) {
            //这个时候的情况是已经要准考证生成的状态了但是没有安排完，基于原来的状态自动安排
            try {
                if (!examService.checkStatus(examInfo, "canGenerate")) {
                    return;
                }
                if (!roomService.checkAndGenerateExamRoom(examInfo)) {
                    return;
                }

                Map<String, Queue> dataMap = examRelatedPeopleService.analyseReviewerAndInvigilators(examInfo);
                if (!examRelatedPeopleService.checkAndGenerateInvigilator(examInfo, dataMap)) {
                    return;
                }

                if (!examRelatedPeopleService.checkAndGenerateReviewer(examInfo, dataMap)) {
                    return;
                }
                if (!examTicketService.generateTickets(examInfo)) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                examInfo.setStatus(examStatus.FAILED.getCode());
                examMapper.update(examInfo);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } finally {

            }
        }
        if (examStatus.GENERATE_FINISH.getCode().equals(examInfo.getStatus())) {
            examTicketService.generateTickets(examInfo);
        }
        if (!examStatus.FAILED.getCode().equals(examInfo.getStatus())) {
            exam exam = new exam();
            exam.setId(examId);
            exam.setStatus(status);
            examMapper.update(exam);
        }
    }
}
    