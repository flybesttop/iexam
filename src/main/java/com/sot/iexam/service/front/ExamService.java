package com.sot.iexam.service.front;

import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.examTicket;
import com.sot.iexam.VO.ticketVo;
import com.sot.iexam.controller.RegistrationController;

import java.util.List;
import java.util.Map;
/**
 * @author Kimbobo
 */
public interface ExamService {

    boolean deleteExam(Integer examId);

    Map getAllStdTicket(Integer examRoomId);

    ticketVo getTicket(Integer userId,Integer examId);

    Map getExamInfoListToReviewer(Integer page,Integer size,String search,Integer userId);

    Map getExamInfoListToPublish(Integer page,Integer size,String search);

    Map getExamInfoListMyExams(Integer page, Integer size,Integer userId);

    Map getExamInfoListWithTitle(Integer page, Integer size, exam exam);
    /**
     * 添加考试信息
     *
     * @param exam 考试信息实体 格式见{@link exam}
     * @return 添加成功返回true
     */
    boolean addExam(exam exam);

    /**
     * 获取考试信息
     *
     * @param id 传入考试id
     * @return 对应id的考试信息
     */
    exam getExamInfo(Integer id);

    /**
     * 获取考试信息分页
     * 页数从1开始，不传值默认第一页 大小10
     *
     * @param page 页号 （从1开始）
     * @param size 页大小
     * @return 返回分页中的考试信息列表
     */
    Map getExamInfoList(Integer page, Integer size, exam exam);

    /**
     * 更新考试信息
     */
    boolean updateExam(exam exam);

    boolean updateExamAndScheduler(exam exam);
    /**
     * 更新考试信息（只更新状态和时间）
     */
    boolean updateStatus(exam exam);

    boolean checkStatus(exam exam,String status);

    /**
     * 根据考生的id,获取考试的考试信息列表（这个考试的信息是已经生成了准考证的信息）
     * 对于没有生成准考证的信息，一律为报名信息 {@link RegistrationController#getRegistrationByExamineesId}
     */
    Map getExamVoList(Integer page, Integer size, examTicket examTicket);
}
