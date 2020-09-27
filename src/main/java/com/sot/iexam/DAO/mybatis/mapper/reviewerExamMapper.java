package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.reviewerExam;
import com.sot.iexam.DO.reviewerExamRoom;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface reviewerExamMapper {

    void insert(@Param("reviewerExam") reviewerExam reviewerExam);

    void update(@Param("reviewerExam") reviewerExam reviewerExam);

    List<reviewerExam> getReviewerExamList(@Param("reviewerExam")reviewerExam conditionsInObj);

    Integer getReviewerExamListCount(@Param("reviewerExam")reviewerExam conditionsInObj);

    void deleteByExamId(Integer examId);

    reviewerExam getReviewerManage(Integer reviewerId, Integer examId);


    void deleteById(Integer id);
}
