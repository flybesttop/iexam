package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.title;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface titleMapper {

    List<title> getTitleList(@Param("title") title title);

    title getTitleByTitleId(Integer titleId);

    Integer getCount(@Param("title") title title);

    Integer insert(@Param("title") title title);

    void update(@Param("title")title title);
}
