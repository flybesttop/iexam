package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.XUser;
import com.sot.iexam.DO.examRoom;
import com.sot.iexam.DO.reviewerExamRoom;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Kimbobo
 * @Date: 2019/11/28 16:08
 */
@Repository
public interface reviewerExamRoomMapper {
    void insert(@Param("reviewerExamRoom") reviewerExamRoom reviewerExamRoom);

    void resetReviewer(Integer examId);

    List<examRoom> getByReviewerExamId(Integer reviewerExamId);

    void cancelReviewerExamRoom(Integer reviewerExamId, Integer examRoomId);

    XUser getXuerByExamRoomId(Integer examRoomId);

    void deleteByReviewerExamId(Integer reviewerExamId);

    List<Integer> getManagedRoomByReviewerExamId(Integer id);

    void deleteByExamRoomId(Integer id);
}
