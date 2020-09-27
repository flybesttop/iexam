package com.sot.iexam.service.front;

import com.sot.iexam.DO.examNews;

import java.util.List;
import java.util.Map;

/**
 * @author Kimbobo
 */
public interface ExamNewsService {


    /**
     * 添加考试新闻信息
     *
     * @param examNews 考试新闻实体 {@link examNews}
     * @return 添加成功返回true，失败false
     */
    boolean addExamNews(examNews examNews);

    /**
     * 分页获取考试新闻，不填默认第一页分10个
     * 没有具体的新闻详细信息，只有title！
     *
     * @param page 页数 （从1开始）
     * @param size 页号
     * @return 考试新闻列表
     */
    Map getExamNewsListVO(Integer page, Integer size, examNews examNews);

    /**
     * 根据考试新闻id获取具体的考试新闻信息     * 带详细信息
     *
     * @param newsId 考试新闻id
     * @return
     */
    examNews getExamNewsInfoById(Integer newsId);

    Map getExamNewsInfoByIdPrevAndNext(Integer newsId);

    void updateNews(examNews examNews);

    void deleteById(Integer id);

    Map getExamNewsInfoToDoorIndex();
}
