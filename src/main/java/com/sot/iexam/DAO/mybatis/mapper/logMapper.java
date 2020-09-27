package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.log;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Kimbobo
 * @Date: 2019/12/10 8:27
 */
@Repository
public interface logMapper {
    void insert(@Param("log") log log);
    List<log> getLogListByType(Integer type);
    Integer getLogListByTypeCount(Integer type);
}
