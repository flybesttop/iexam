package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.grade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Kimbobo
 * @Date: 2019/12/2 10:18
 */
@Repository
public interface gradeMapper {

    void insert(@Param("grade") grade grade);

    void update(@Param("grade") grade grade);

    List<grade> getGradeList(@Param("grade") grade grade);

    Integer getGradeListCount(@Param("grade") grade grade);

    List<grade> getGradeListByExamRoomId(Integer examRoomId);

    grade getTicket(Integer userId,Integer examId);
}
