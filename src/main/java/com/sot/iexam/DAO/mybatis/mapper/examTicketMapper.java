package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.examTicket;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface examTicketMapper {

    void insert(@Param("ticket") examTicket ticket);

    Integer getTicketNumberByExamId(Integer id);

    examTicket getTicket(Integer userId,Integer examId);
}
