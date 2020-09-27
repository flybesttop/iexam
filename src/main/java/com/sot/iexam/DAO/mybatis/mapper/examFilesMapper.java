package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.examFiles;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Kimbobo
 * @Date: 2019/12/1 22:22
 */
@Repository
public interface examFilesMapper {

    List<examFiles> getFileList(@Param("examFiles") examFiles examFiles);

    Object getCount(@Param("examFiles")examFiles examFiles);

    void insert(@Param("examFiles")examFiles examFiles);
}
