package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.examinees;
import com.sot.iexam.VO.examineesListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Kimbobo
 */
@Repository
public interface examineesMapper {

    Integer checkExistence(@Param("examinees") examinees examinees);

    void insertUser(@Param("examinees") examinees examinees);

    Integer insert(@Param("examinees") examinees examinees);

    examinees getExamineesInfoById(Integer examineesId);

    List<examineesListVo> getExamineesList(@Param("examinees") examinees examinees);

    Integer getCount(@Param("examinees") examinees examinees);

    void update(@Param("examinees") examinees examinees);

    examinees findByAccountAndPassword(String account,String password);

    List<examinees> getExamineesListAll();
}
