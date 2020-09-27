package com.sot.iexam.DAO.mybatis.mapper;


import com.sot.iexam.DO.XUser;
import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.reviewerExam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface reviewerMapper {
    List<Integer> getReviewersIdByTitleId(Integer titleId);


    Integer checkTime(Integer reviewId, String startTime, String endTime);

    List<reviewerExam> getManagedReviewersByExamId(Integer examId);

    List<Integer> getUnAvailableReviewers(String startTime, String endTime);


    void disableReviewer(Integer userId);

    Integer getReviewerAssignment(Integer userId);

    void enableReviewer(Integer userId);

    XUser getReviewerById(Integer reviewerId);
}
