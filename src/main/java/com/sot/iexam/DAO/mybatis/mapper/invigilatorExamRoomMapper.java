package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.XUser;
import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.invigilatorExamRoom;
import com.sot.iexam.VO.roomVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface invigilatorExamRoomMapper {
    List<invigilatorExamRoom> getInvigilatorByExamId(Integer examId);

    List<Integer> getUnAvailableInvigilators(String startTime, String endTime);

    void insert(@Param("invigilatorExamRoom") invigilatorExamRoom invigilatorExamRoom);

    void update(@Param("invigilatorExamRoom") invigilatorExamRoom invigilatorExamRoom);

    List<invigilatorExamRoom> getInvigilatorExamList(@Param("invigilatorExamRoom") invigilatorExamRoom conditionsInObj);

    Integer getInvigilatorExamListCount(@Param("invigilatorExamRoom") invigilatorExamRoom conditionsInObj);

    void deleteByExamId(Integer examId);

    List<roomVo> getArrangedInvigilator(@Param("invigilatorExamRoom") invigilatorExamRoom conditionsInObj);

    Integer getReviewerExamArrangeCount(@Param("invigilatorExamRoom") invigilatorExamRoom conditionsInObj);

    XUser getInvigilatorInfoById(Integer invigilatorId);

    void deleteByExamIdAndInvigilatorId(@Param("invigilatorExamRoom") invigilatorExamRoom invigilatorExamRoom);

    List<XUser> getInvigilatorsByExamRoomId(Integer examRoomId);

    void deleteByExamRoomId(Integer id);
}
