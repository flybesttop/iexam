package com.sot.iexam.service.front.impl;

import com.sot.iexam.DAO.mybatis.mapper.newsFilesMapper;
import com.sot.iexam.DO.newsFiles;
import com.sot.iexam.service.front.NewsFilesService;
import com.sot.iexam.util.Timmer;
import com.zlzkj.core.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsFilesServiceImpl implements NewsFilesService {

    @Autowired
    newsFilesMapper newsFilesMapper;

    @Override
    public void insert(newsFiles newsFiles) {
        String time = Timmer.now();
        newsFiles.setCreateTime(time);
        newsFiles.setCreateTime(time);
        newsFilesMapper.insert(newsFiles);
    }

    @Override
    public void deleteByNewsId(Integer newsId) {
        newsFilesMapper.deleteByNewsId(newsId);
    }

    @Override
    public List<newsFiles> getFilesByNewsId(Integer newsId) {
        List<newsFiles> fileList = newsFilesMapper.getFileList(newsId);
        for (newsFiles newsFiles : fileList) {
            newsFiles.setFilePath(UploadUtils.parseFileUrl(newsFiles.getFilePath()));

        }
        return fileList;
    }
}
    