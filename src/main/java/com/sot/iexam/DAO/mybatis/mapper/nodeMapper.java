package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.XNode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface nodeMapper {
    List<XNode> selectAll();
}
