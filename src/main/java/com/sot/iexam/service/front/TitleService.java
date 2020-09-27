package com.sot.iexam.service.front;

import com.sot.iexam.DO.title;

import java.util.List;
import java.util.Map;
/**
 * @author Kimbobo
 */
public interface TitleService {
    /**
     * 分页获取考试头衔，不填默认第一页分10个
     *
     * @param page 页数 （从1开始）
     * @param size 页号
     */
    Map getTitleList(Integer page, Integer size,title title);

    title getTitleByTitleId(Integer titleId);

    boolean addTitle(title title);

    void updateTitle(title title);

    void deleteTitle(Integer titleId);
}
