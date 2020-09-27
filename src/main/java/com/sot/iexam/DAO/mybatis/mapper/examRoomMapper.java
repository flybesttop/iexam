package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.classRoom;
import com.sot.iexam.DO.examRoom;
import com.sot.iexam.VO.roomVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface examRoomMapper {

    public List<classRoom> getExamRoomArrangement(Integer examId);

    public List<examRoom> getExamRoomInfoByExamId(Integer examId);

    void insert(@Param("examRoom") examRoom examRoom);

    void update(@Param("examRoom") examRoom examRoom);

    List<examRoom> getExamRoomByExamId(Integer id);

    roomVo getExamRoomById(Integer examRoomId);

    void deleteByExamRoomId(Integer examRoomId);


    void deleteByExamId(Integer examId);

    examRoom getExamRoomByIdSingle(Integer examRoomId);
}