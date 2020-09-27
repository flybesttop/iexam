package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.examTicket;
import com.sot.iexam.VO.examListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface examMapper {
    /**
     * 根据id获取考试信息
     */
    public exam getExamById(int id);

    /**
     * 插入考试信息 格式如{@link exam}
     */
    Integer insert(@Param("exam") exam exam);

    /**
     * 分页获取考试信息
     */
    List<exam> getExamList(@Param("exam") exam exam);

    List<exam> getExamListToPublish(String search);

    Integer getCountToPublish(String search);

    List<exam> getExamInfoListRegister(@Param("exam") exam exam);

    Object getCount(@Param("exam") exam exam);

    void update(@Param("exam") exam exam);

    void updateStatus(@Param("exam") exam exam);

    List<examListVo> getExamVoList(@Param("examTicket") examTicket examineesId);

    Integer getExamVoCount(@Param("examTicket") examTicket examTicket);

    List<exam> getExamlistArrange();


    List<exam> getExamInfoListToReviewer(String search,Integer userId);

    Integer getCountExamInfoListToReviewer(String search,Integer userId);

}
