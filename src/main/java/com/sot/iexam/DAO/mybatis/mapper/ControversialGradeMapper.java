package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.controversialGrade;
import com.sot.iexam.VO.controversialGradeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Kimbobo
 * @Date: 2019/12/9 11:15
 */
@Repository
public interface ControversialGradeMapper {
    void insert(@Param("controversialGrade") controversialGrade controversialGrade);

    void update(@Param("controversialGrade") controversialGrade controversialGrade);

    List<controversialGradeVo> getControversialGradeListVo(@Param("controversialGrade") controversialGrade controversialGrade);

    controversialGrade getCG(Integer gradeId);

    Integer getControversialGradeListVoCount(@Param("controversialGrade") controversialGrade controversialGrade);
}
