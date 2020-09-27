package com.sot.iexam.service.front;

import com.sot.iexam.DO.*;
import com.sot.iexam.VO.examArrangeVo;

import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Kimbobo
 */
public interface ExamRelatedPeopleService {

    List<examArrangeVo> getExamArrangeInfo(Integer examId);

    /**
     * 获取本考试有时间空闲的 监考人和阅卷人的信息
     *
     * @param examInfo 传入考试信息
     * @return 返回三个队列 分别是纯监考的信息，纯阅卷的信息和既能监考又能阅卷的信息
     *
     */
    Map<String, Queue> analyseReviewerAndInvigilators(exam examInfo);

    /**
     * 生成阅卷老师
     *
     * @param examInfo 传入考试信息
     * @param dataMap  有时间空闲的 监考人和阅卷人的信息
     * @return 能生成true 不能false
     */
    boolean checkAndGenerateInvigilator(exam examInfo, Map<String, Queue> dataMap);

    /**
     * 生成监考老师
     *
     * @param examInfo 传入考试信息
     * @param dataMap  有时间空闲的 监考人和阅卷人的信息
     * @return 能生成true 不能false
     */
    boolean checkAndGenerateReviewer(exam examInfo, Map<String, Queue> dataMap);

    /**
     * 获取监考安排列表
     *
     * @param page            页 默认1
     * @param size            大小 默认10
     * @param conditionsInObj {@link invigilatorExamRoom}
     * @return 返回的map中有总记录数和 监考安排分页的记录
     */
    Map getInvigilatorExamList(Integer page, Integer size, invigilatorExamRoom conditionsInObj);

    /**
     * @param page            页 默认1
     * @param size            大小 默认10
     * @param conditionsInObj {@link reviewerExam}
     * @return 返回的map中有总记录数和 阅卷安排分页的记录
     */
    Map getReviewerExamListVo(Integer page, Integer size, reviewerExam conditionsInObj);

    Map getArrangedInvigilator(Integer page, Integer size, invigilatorExamRoom conditionsInObj);

    List<XUser> getPeopleInfo(List list);

    Map getPeopleInfoPage(List list,Integer page,Integer size);

    String resetInvigilator(Integer examId);

    Map addInvigilatorExamRoom(invigilatorExamRoom invigilatorExamRoom, Map map);

    void cancelInvigilatorExam(invigilatorExamRoom invigilatorExamRoom, Map map);

    String resetReviewer(Integer examId);

    Map getReviewerExamList(Integer page, Integer size, reviewerExam conditionsInObj);

    void cancelReviewerExamRoom(Integer reviewerExamId, Integer examRoomId, Integer examId, Map map);

    void addReviewerExamRoom(reviewerExamRoom reviewerExamRoom);

    void cancelReviewerExam(Integer reviewerExamId, Integer examId, Map map);

    void addReviewerExam(reviewerExam reviewerExam, Map map);
}

