package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.examFiles;
import com.sot.iexam.DO.newsFiles;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * @Author: Kimbobo
 * @Date: 2019/12/1 22:22
 */
@Repository
public interface newsFilesMapper {

    List<newsFiles> getFileList(Integer newsId);

    Object getCount(@Param("newsFiles") newsFiles newsFiles);

    void insert(@Param("newsFiles") newsFiles newsFiles);

    void deleteByNewsId(Integer newsId);
}
