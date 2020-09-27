package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.examinees;
import com.sot.iexam.DO.registration;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface registrationMapper {
    public List<registration> getRegistrationByExamId(Integer id);

    Integer checkExistence(@Param("registration") registration registration);

    Integer insert(@Param("registration") registration registration);

    List<exam> getRegistrationByExamineesId(Integer examineesId);

    List<exam> getRegistrationByExamineesIdMyExams(Integer examineesId);

    registration getRegistrationById(Integer registrationId);

    List<examinees> getRegistrationList(@Param("registration") registration registration);

    Integer getCountByExamineesId(Integer examineesId);

    Integer getCountRegistrationByExamineesIdMyExams(Integer examineesId);

    Integer getCountByExamId(Integer examId);

    Integer getCount(@Param("registration") registration registration);

    void update(@Param("registration") registration registration);

    void deleteByRegistrationId(Integer id);
}
