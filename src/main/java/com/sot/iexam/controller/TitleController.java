package com.sot.iexam.controller;

import com.sot.iexam.DO.examinees;
import com.sot.iexam.DO.title;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.front.FileService;
import com.sot.iexam.service.front.TitleService;
import com.sot.iexam.util.BeanUtils;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kimbobo
 */
@Controller
@RequestMapping("title")
public class TitleController {

    @Autowired
    TitleService titleService;
    @Autowired
    FileService fileService;

    @LogAnnotation(needData = false, operateType = "添加职称")
    @RequestMapping("addTitle")
    public void addTitle(HttpServletResponse response, @RequestBody title title) {
        titleService.addTitle(title);
        ResponseUtil.ajaxReturn(response, title, "", 1);
    }

    @LogAnnotation(needData = false, operateType = "上传职称图片")
    @RequestMapping("uploadImg")
    public void uploadImg(HttpServletResponse response, @RequestParam("img") MultipartFile multipartFile, @RequestParam("titleId") Integer titleId) {
        title title = titleService.getTitleByTitleId(titleId);
        Map<String, Object> data = new HashMap<>(8);
        boolean flag = fileService.uploadMultipartFile(data, multipartFile);
        if (flag) {//上传成功
            ResponseUtil.ajaxReturn(response, data, "上传完毕", 1);
            title.setPhotoPath(data.get("imgUrl").toString());
            titleService.updateTitle(title);
        } else {
            ResponseUtil.ajaxReturn(response, null, data.get("errorMessage").toString(), 0);
        }
    }

    @LogAnnotation(needData = false, operateType = "修改职称")
    @RequestMapping("updateTitle")
    public void updateTitle(HttpServletResponse response, @RequestBody title title) {
        titleService.updateTitle(title);
        ResponseUtil.ajaxReturn(response, null, "", 1);
    }

    @LogAnnotation(needData = false, operateType = "删除职称")
    @RequestMapping("deleteTitle")
    public void deleteTitle(HttpServletResponse response, @RequestParam("titleId") Integer titleId) {
        titleService.deleteTitle(titleId);
        ResponseUtil.ajaxReturn(response, null, "", 1);
    }

    /**
     * 分页获取考试头衔，不填默认第一页分10个
     *
     * @param map //    {
     *            //        "page": 页号,
     *            //        "size": 大小,
     *            //        "title": {
     *            //            {@link title}
     *            //        }
     *            //    }
     */
    @LogAnnotation(needData = false, operateType = "获取职称列表")
    @RequestMapping("getTitleList")
    @SuppressWarnings("unchecked")
    public void getTitleList(HttpServletResponse response, @RequestBody Map map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        title ConditionsInObj = BeanUtils.map2Bean((LinkedHashMap) map.get("title"), title.class);
        Map m = titleService.getTitleList(page, size, ConditionsInObj);
        ResponseUtil.ajaxReturn(response, m);
    }

    /**
     * 根据头衔id获取头衔信息
     *
     * @param titleId 头衔id
     */
    @LogAnnotation(needData = false, operateType = "根据职称id获取职称信息")
    @RequestMapping("getTitleByTitleId")
    public void getTitleByTitleId(HttpServletResponse response, Integer titleId) {
        title title = titleService.getTitleByTitleId(titleId);
        ResponseUtil.ajaxReturn(response, title);
    }
}
    