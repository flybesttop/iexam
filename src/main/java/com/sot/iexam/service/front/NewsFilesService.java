package com.sot.iexam.service.front;

import com.sot.iexam.DO.newsFiles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Kimbobo
 * @Date: 2019/12/7 15:44
 */
public interface NewsFilesService {
    void insert( newsFiles newsFiles);

    void deleteByNewsId(Integer id);

    List<newsFiles> getFilesByNewsId(Integer newsId);
}
