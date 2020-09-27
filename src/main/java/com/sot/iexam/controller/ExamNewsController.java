package com.sot.iexam.controller;

import com.sot.iexam.DO.examNews;
import com.sot.iexam.DO.newsFiles;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.enums.examNewsTypes;
import com.sot.iexam.service.front.ExamNewsService;
import com.sot.iexam.service.front.FileService;
import com.sot.iexam.service.front.NewsFilesService;
import com.sot.iexam.util.BeanUtils;
import com.sot.iexam.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kimbobo
 */

@Controller
@RequestMapping("examNews")
public class ExamNewsController {

    @Autowired
    ExamNewsService examNewsService;

    @Autowired
    NewsFilesService newsFilesService;

    /**
     * 添加考试新闻信息
     * 如果要获取考试对应的头衔信息，参考{@link TitleController#getTitleList(HttpServletResponse, Map)}
     * 新闻的类型详细参照 {@link examNewsTypes}
     * 如果要获取已经存在的考试信息 参考{@link ExamController#getExamInfoList(HttpServletResponse, Map)}
     *
     * @param examNews 考试新闻实体 {@link examNews}
     */
    @LogAnnotation(needData = false, operateType = "添加新闻")
    @RequestMapping("addNews")
    public void addNews(HttpServletResponse response, @RequestBody examNews examNews) {
        examNewsService.addExamNews(examNews);
        ResponseUtil.ajaxReturn(response, examNews, "新闻添加成功", 1);
    }

    @LogAnnotation(needData = false, operateType = "更新新闻")
    @RequestMapping("updateNews")
    public void updateNews(HttpServletResponse response, @RequestBody examNews examNews) {
        examNewsService.updateNews(examNews);
        ResponseUtil.ajaxReturn(response, null, "", 1);
    }

    /**
     * 分页获取考试新闻，不填默认第一页分10个
     * 没有具体的新闻详细信息，只有title！
     *
     * @param map //    {
     *            //        "page": 页号,
     *            //        "size": 大小,
     *            //        "examNews": {
     *            //            {@link examNews}
     *            //        }
     *            //    }
     */
    @LogAnnotation(needData = false, operateType = "获取新闻列表")
    @RequestMapping("getExamNewsList")
    @SuppressWarnings("unchecked")
    public void getExamNewsList(HttpServletResponse response, @RequestBody Map map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        examNews conditionsInObj = BeanUtils.map2Bean((LinkedHashMap) map.get("examNews"), examNews.class);
        Map result = examNewsService.getExamNewsListVO(page, size, conditionsInObj);
        ResponseUtil.ajaxReturn(response, result);
    }

    /**
     * 根据考试新闻id获取具体的考试新闻信息     * 带详细信息
     *
     * @param newsId 考试新闻id
     */
    @LogAnnotation(needData = false, operateType = "根据id获取新闻信息")
    @RequestMapping("getExamNewsInfoById")
    public void getExamNewsInfoById(HttpServletResponse response, Integer newsId) {
        examNews newsInfo = examNewsService.getExamNewsInfoById(newsId);
        ResponseUtil.ajaxReturn(response, newsInfo);
    }

    @LogAnnotation(needData = false, operateType = "获取门户网站首页的所有新闻信息")
    @RequestMapping("getExamNewsInfoToDoorIndex")
    public void getExamNewsInfoToDoorIndex(HttpServletResponse response) {
        Map newsInfo = examNewsService.getExamNewsInfoToDoorIndex();
        ResponseUtil.ajaxReturn(response, newsInfo);
    }

    @LogAnnotation(needData = false, operateType = "根据id获取新闻信息包含上一条和下一条")
    @RequestMapping("getExamNewsInfoByIdPrevAndNext")
    public void getExamNewsInfoByIdPrevAndNext(HttpServletResponse response, Integer newsId) {
        Map newsInfo = examNewsService.getExamNewsInfoByIdPrevAndNext(newsId);
        ResponseUtil.ajaxReturn(response, newsInfo);
    }

    @LogAnnotation(needData = false, operateType = "删除新闻")
    @RequestMapping("deleteNews")
    public void deleteNews(HttpServletResponse response, Integer id) {
        examNewsService.deleteById(id);
        newsFilesService.deleteByNewsId(id);
        ResponseUtil.ajaxReturn(response, null, "删除完毕", 1);
    }

    @LogAnnotation(needData = false, operateType = "访问新闻详细页面")
    @RequestMapping("gotoNewsDetail")
    public String gotoNewsDetail(Model model, @RequestParam("newsId") Integer newsId) {

        Map newsInfo = examNewsService.getExamNewsInfoByIdPrevAndNext(newsId);
        model.addAttribute("newsInfo",newsInfo);
        return "door-view/news_info";
    }

    @LogAnnotation(needData = false, operateType = "访问门户网站新闻总览页面")
    @RequestMapping("informations")
    public String informations(Model model, @RequestParam("type") Integer type) {
        if (type==null){
            type=1;
        }
        model.addAttribute("type",type);
        return "door-view/informations";
    }

}
    