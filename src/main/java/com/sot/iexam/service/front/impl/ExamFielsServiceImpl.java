package com.sot.iexam.service.front.impl;

import com.github.pagehelper.PageHelper;
import com.sot.iexam.DAO.mybatis.mapper.examFilesMapper;
import com.sot.iexam.DO.examFiles;
import com.sot.iexam.VO.examineesListVo;
import com.sot.iexam.service.front.ExamFilesService;
import com.sot.iexam.util.Timmer;
import com.zlzkj.core.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kimbobo
 * @Date: 2019/12/1 22:23
 */
@Service
public class ExamFielsServiceImpl implements ExamFilesService {

    @Autowired
    examFilesMapper examFilesMapper;

    @Override
    public Map getFiles(Integer page, Integer size, examFiles examFiles) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);
        PageHelper.startPage(page, size);
        List<examFiles> fileList = examFilesMapper.getFileList(examFiles);
        for (examFiles f : fileList) {
            if (!"".equals(f.getFilePath()) || f.getFilePath() == null) {
                f.setFileName(f.getFilePath());
                f.setFilePath(UploadUtils.parseFileUrl(f.getFilePath()));
            }
        }
        m.put("data", fileList);
        m.put("count", examFilesMapper.getCount(examFiles));
        return m;

    }

    @Override
    public void insert(examFiles examFiles) {
        String time = Timmer.now();
        examFiles.setCreateTime(time);
        examFiles.setModifyTime(time);
        examFilesMapper.insert(examFiles);
    }
}
    