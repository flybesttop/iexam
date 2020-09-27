package com.sot.iexam.controller;

import com.sot.iexam.DO.controversialGrade;
import com.sot.iexam.DO.title;
import com.sot.iexam.VO.controversialGradeVo;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.front.ControversialGradeService;
import com.sot.iexam.util.BeanUtils;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kimbobo
 */
@Controller
@RequestMapping("controversialGrade")
public class ControversialGradeController {
    @Autowired
    ControversialGradeService controversialGradeService;

    @LogAnnotation(needData = false, operateType = "添加异常成绩记录")
    @RequestMapping("addControversialGrade")
    public void addControversialGrade(HttpServletResponse response, @RequestBody controversialGrade controversialGrade) throws Exception {
        //controversialGrade conditionsInObj = BeanUtils.map2Bean((LinkedHashMap) map.get("controversialGrade"), controversialGrade.class);
        controversialGradeService.addControversialGrade(controversialGrade);
        ResponseUtil.ajaxReturn(response, controversialGrade, "", 1);
    }

    /**
     * 异常成绩申报修改直接用这个方法 respond就在这个表里
     *
     * @param controversialGrade {@link controversialGrade}
     */
    @LogAnnotation(needData = false, operateType = "修改异常成绩记录")
    @RequestMapping("updateControversialGrade")
    public void updateControversialGrade(HttpServletResponse response, @RequestBody controversialGrade controversialGrade) {

        controversialGradeService.updateControversialGrade(controversialGrade);
        ResponseUtil.ajaxReturn(response, controversialGrade, "", 1);
    }

    /**
     * 获取异常成绩列表
     *
     * @param map //    {
     *            //        "page": 页号,
     *            //        "size": 大小,
     *            //        "controversialGrade": {
     *            //            {@link controversialGrade}
     *            //        }
     *            //    }
     *            返回值为 {@link controversialGradeVo},
     */
    @LogAnnotation(needData = false, operateType = "获取异常成绩列表")
    @RequestMapping("getControversialGradeList")
    @SuppressWarnings("unchecked")
    public void getControversialGradeList(HttpServletResponse response, @RequestBody Map map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        controversialGrade conditionsInObj = BeanUtils.map2Bean((LinkedHashMap) map.get("controversialGrade"), controversialGrade.class);
        Map m = controversialGradeService.getControversialGradeList(page, size, conditionsInObj);
        ResponseUtil.ajaxReturn(response, m);
    }
}
    