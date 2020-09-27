package com.sot.iexam.controller;

import com.sot.iexam.DO.log;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.front.LogService;
import com.sot.iexam.util.BeanUtils;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Kimbobo
 */
@Controller
@RequestMapping("log")
public class LogController {
    @Autowired
    LogService logService;

    @LogAnnotation(needData=true,operateType = "获取日志")
    @RequestMapping("getLogList")
    @SuppressWarnings("unchecked")
    public void getLogList(HttpServletResponse response,@RequestBody Map map){
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        Integer type = BeanUtils.mapString2Integer(map.get("type"));
        Map m=logService.getLoglist(page,size,type);
        ResponseUtil.ajaxReturn(response, m, "", 1);
    }

}
