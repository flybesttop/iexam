package com.sot.iexam.controller;

import com.sot.iexam.DO.XDictionary;
import com.sot.iexam.service.back.XDictionaryService;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * @author Kimbobo
 */
@Controller
@RequestMapping("xDictionary")
public class XDictionaryController {
    @Autowired
    XDictionaryService xDictionaryService;


    @RequestMapping("insert")
    public void insert(HttpServletResponse response, @RequestBody XDictionary emoDictionary) {

        xDictionaryService.insert(emoDictionary);
        ResponseUtil.ajaxReturn(response, emoDictionary, "此字典信息 插入完毕", 1);
    }


    @RequestMapping("deleteRestful/{id}")
    public void deleteRestful(HttpServletResponse response, @PathVariable("id") int id) {
        xDictionaryService.deleteById(id);
        ResponseUtil.ajaxReturn(response, "", "此字典信息 已清除", 1);
    }


    @RequestMapping("delete")
    public void delete(HttpServletResponse response, int id) {
        xDictionaryService.deleteById(id);
        ResponseUtil.ajaxReturn(response, "", "此字典信息 已清除", 1);
    }


    @RequestMapping("modify")
    public void modify(HttpServletResponse response, @RequestBody XDictionary emoDictionary) {
        xDictionaryService.modify(emoDictionary);
        ResponseUtil.ajaxReturn(response, emoDictionary, "此字典信息 修改完毕", 1);
    }


    @RequestMapping("find")
    public void find(HttpServletResponse response,
                     String createTimeStart, String createTimeEnd, String modifiedTimeStart, String modifiedTimeEnd,
                     String code, String value, String type, String detail, String id,
                     String direction, String orderBy, String limitPage, String limitSize) {
        List<XDictionary> emoCartProducts = xDictionaryService.find(
                createTimeStart, createTimeEnd, modifiedTimeStart, modifiedTimeEnd,
                code, value, type, detail, id,
                direction, orderBy, limitPage, limitSize);
        ResponseUtil.ajaxReturn(response, emoCartProducts, "此字典信息 查找完毕", 1);
    }

    @RequestMapping("findForTable")
    public void findForTable(HttpServletResponse response,
                             String createTimeStart, String createTimeEnd, String modifiedTimeStart, String modifiedTimeEnd,
                             String code, String value, String type, String detail, String id,
                             String direction, String orderBy, String limitPage, String limitSize) {
        Map map = xDictionaryService.findForTable(
                createTimeStart, createTimeEnd, modifiedTimeStart, modifiedTimeEnd,
                code, value, type, detail, id,
                direction, orderBy, limitPage, limitSize);
        ResponseUtil.ajaxReturn(response, map);
    }

    @RequestMapping("findForColumns")
    public void findForColumns(HttpServletResponse response) throws IllegalAccessException {

        XDictionary column = xDictionaryService.findForColumns();
        ResponseUtil.ajaxReturn(response, column);
    }

    @RequestMapping("findDataBasePre")
    public void findDataBasePre(HttpServletResponse response,
                                String code, String type) {
        List<XDictionary> emoCartProducts = xDictionaryService.findDataBasePre(code, type);
        ResponseUtil.ajaxReturn(response, emoCartProducts);
    }

    @RequestMapping("changeFormBack")
    public void changeFormBack(HttpServletResponse response, @RequestBody XDictionary[] XDictionaries) {
        xDictionaryService.changeFormBack(XDictionaries);
        ResponseUtil.ajaxReturn(response, "1500", "修改完毕 页面将在1.5s后重新刷新", 1);
    }
}
