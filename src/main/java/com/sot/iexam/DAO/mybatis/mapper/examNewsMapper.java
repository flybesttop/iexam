package com.sot.iexam.DAO.mybatis.mapper;

import com.sot.iexam.DO.examNews;
import com.sot.iexam.VO.examNewsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface examNewsMapper {
    /**
     * 添加考试新闻
     *
     * @param examNews 添加的考试新闻实体 {@link examNews}
     */
    Integer insert(@Param("examNews") examNews examNews);

    List<examNewsVo> getExamNewsListVO(@Param("examNews") examNews examNews);

    examNews getExamNewsInfoById(Integer newsId);
    examNews getPrev(Integer newsId);
    examNews getNext(Integer newsId);

    List<examNews> getDoorIndexNewsList(Integer type);

    Integer getCount(@Param("examNews") examNews examNews);

    void update(@Param("examNews") examNews examNews);

    void deleteById(Integer id);
}
