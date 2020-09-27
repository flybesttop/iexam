package com.sot.iexam.service.front.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.sot.iexam.DAO.jpa.XUserRepository;
import com.sot.iexam.DAO.jpa.XUserRoleRepository;
import com.sot.iexam.DAO.mybatis.mapper.examNewsMapper;
import com.sot.iexam.DAO.mybatis.mapper.newsFilesMapper;
import com.sot.iexam.DO.XUser;
import com.sot.iexam.DO.examNews;
import com.sot.iexam.DO.newsFiles;
import com.sot.iexam.VO.examNewsVo;
import com.sot.iexam.service.front.ExamNewsService;
import com.sot.iexam.util.Timmer;
import com.zlzkj.core.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kimbobo
 */
@Service
public class ExamNewsServiceImpl implements ExamNewsService {
    @Autowired
    examNewsMapper examNewsMapper;
    @Autowired
    newsFilesMapper newsFilesMapper;
    @Autowired
    XUserRepository xUserRepository;

    @Override
    public boolean addExamNews(examNews examNews) {

        String time = Timmer.now();
        examNews.setStatus(0);
        examNews.setPublishTime(time);
        examNews.setCreateTime(time);
        examNews.setModifyTime(time);
        examNewsMapper.insert(examNews);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map getExamNewsListVO(Integer page, Integer size, examNews examNews) {
        Map m = new HashMap();
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        PageHelper.startPage(page, size);
        m.put("data", examNewsMapper.getExamNewsListVO(examNews));
        m.put("count", examNewsMapper.getCount(examNews));
        return m;
    }

    @Override
    public examNews getExamNewsInfoById(Integer newsId) {
        return examNewsMapper.getExamNewsInfoById(newsId);
    }

    @Override
    public Map getExamNewsInfoByIdPrevAndNext(Integer newsId) {

        examNews examNews=examNewsMapper.getExamNewsInfoById(newsId);
        examNews.setPublishName(xUserRepository.findByIdToNews(examNews.getPublishId()).getRealName());

        List<newsFiles> newsFiles=newsFilesMapper.getFileList(newsId);
        for (newsFiles nn:newsFiles) {
            nn.setFilePath(UploadUtils.parseFileUrl(nn.getFilePath()));
        }
        examNews.setNewsFilesList(newsFilesMapper.getFileList(newsId));
        examNews examNewsPrev=examNewsMapper.getPrev(newsId);
        examNews examNewsNext=examNewsMapper.getNext(newsId);

        Map m=new HashMap();
        m.put("nowExamNews",examNews);
        m.put("prevExamNews",examNewsPrev);
        m.put("nextExamNews",examNewsNext);

        return m;
    }

    @Override
    public void updateNews(examNews examNews) {
        examNews.setModifyTime(Timmer.now());
        examNewsMapper.update(examNews);
    }

    @Override
    public void deleteById(Integer id) {
        examNewsMapper.deleteById(id);
    }

    @Override
    public Map getExamNewsInfoToDoorIndex() {
        Map m=new HashMap();
        List<examNews> news_List=examNewsMapper.getDoorIndexNewsList(1);
        List<examNews> recruitment_List=examNewsMapper.getDoorIndexNewsList(2);
        List<examNews> information_List=examNewsMapper.getDoorIndexNewsList(3);

        m.put("news_List",news_List);
        m.put("recruitment_List",recruitment_List);
        m.put("information_List",information_List);

        return m;
    }

}
    