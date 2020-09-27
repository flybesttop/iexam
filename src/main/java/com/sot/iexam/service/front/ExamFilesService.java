package com.sot.iexam.service.front;

import com.sot.iexam.DO.examFiles;

import java.util.Map;

/**
 * @Author: Kimbobo
 * @Date: 2019/12/1 22:23
 */
public interface ExamFilesService {
    Map getFiles(Integer page, Integer size, examFiles conditionsInObj);

    void insert(examFiles examFiles);
}
