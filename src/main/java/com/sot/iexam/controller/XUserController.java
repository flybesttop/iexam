package com.sot.iexam.controller;

import com.sot.iexam.DO.XUser;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.back.XUserService;
import com.sot.iexam.util.BeanUtils;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * @author Kimbobo
 */
@Controller
@RequestMapping("xUser/")
public class XUserController {
    @Autowired
    XUserService xUserService;

    @LogAnnotation(needData = false, operateType = "根据职称id获取用户信息")
    @RequestMapping("getReviewByTitleId")
    public void getReviewByTitleId(HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        String reviewSearch = (String) map.get("review_search");
        Integer titleId = BeanUtils.mapString2Integer(map.get("titleId"));
        if (reviewSearch == null) {
            reviewSearch = "";
        }
        Map m = xUserService.getReviewByTitleId(page, size, reviewSearch, titleId);
        ResponseUtil.ajaxReturn(response, m);
    }

    @LogAnnotation(needData = false, operateType = "获取所有用户")
    @RequestMapping("getAllInnerUser")
    public void getAllInnerUser(HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
        Integer page = BeanUtils.mapString2Integer(map.get("page"));
        Integer size = BeanUtils.mapString2Integer(map.get("size"));
        String userSearch = (String) map.get("user_search");
        if (userSearch == null) {
            userSearch = "";
        }
        Map m = xUserService.getAllInnerUser(page, size, userSearch);
        ResponseUtil.ajaxReturn(response, m);
    }

    @LogAnnotation(needData = false, operateType = "系统登出")
    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("BACKSYS_USER");
        return "login";
    }

    @LogAnnotation(needData = false, operateType = "查找无该角色用户")
    @RequestMapping("findWithoutRoleId")
    public void findWithoutRoleId(HttpServletResponse response, int roleId, String realName) {
        if (null == realName) {
            realName = "";
        }
        System.out.println("");
        List<XUser> users = xUserService.findWithoutRoleId(roleId, realName);
        ResponseUtil.ajaxReturn(response, users);
    }


    @LogAnnotation(needData = false, operateType = "查找有该角色用户")
    @RequestMapping("findByRoleId")
    public void findByRoleId(HttpServletResponse response, int roleId, String realName) {
        if (null == realName) {
            realName = "";
        }
        List<XUser> users = xUserService.findByRoleId(roleId, realName);
        ResponseUtil.ajaxReturn(response, users);
    }

    @LogAnnotation(needData = false, operateType = "添加系统用户")
    @RequestMapping("insert")
    public void insert(HttpServletResponse response, @RequestBody XUser xUser) {
        xUserService.insert(xUser);
        ResponseUtil.ajaxReturn(response, xUser, "此用户信息 插入完毕", 1);
    }

    @LogAnnotation(needData = false, operateType = "删除系统用户")
    @RequestMapping("deleteRestful/{id}")
    public void deleteRestful(HttpServletResponse response, @PathVariable("id") int id) {
        xUserService.deleteById(id);
        ResponseUtil.ajaxReturn(response, "", "此用户信息 已清除", 1);
    }

    @LogAnnotation(needData = false, operateType = "删除系统用户")
    @RequestMapping("delete")
    public void delete(HttpServletResponse response, int id) {
        xUserService.deleteById(id);
        ResponseUtil.ajaxReturn(response, "", "此用户信息 已清除", 1);
    }

    @LogAnnotation(needData = false, operateType = "修改系统用户")
    @RequestMapping("modify")
    public void modify(HttpServletResponse response, @RequestBody XUser xUser) {
        xUserService.modify(xUser);
        ResponseUtil.ajaxReturn(response, xUser, "此用户信息 修改完毕", 1);
    }


    @RequestMapping("find")
    public void find(HttpServletResponse response,
                     String isEnabled, String password, String phone, String realName,
                     String username, String id,
                     String direction, String orderBy, String limitPage, String limitSize) {
        List<XUser> xUsers = xUserService.find(
                isEnabled, password, phone, realName,
                username, id,
                direction, orderBy, limitPage, limitSize);
        ResponseUtil.ajaxReturn(response, xUsers, "此用户信息 查找完毕", 1);
    }

    @RequestMapping("findForTable")
    public void findForTable(HttpServletResponse response,
                             String isEnabled, String password, String phone, String realName,
                             String username, String id,
                             String direction, String orderBy, String limitPage, String limitSize) {
        Map map = xUserService.findForTable(
                isEnabled, password, phone, realName,
                username, id,
                direction, orderBy, limitPage, limitSize);
        ResponseUtil.ajaxReturn(response, map);
    }

    @RequestMapping("findForColumns")
    public void findForColumns(HttpServletResponse response) throws IllegalAccessException {
        XUser column = xUserService.findForColumns();
        ResponseUtil.ajaxReturn(response, column);
    }

    //@LogAnnotation(needData = false, operateType = "登录")
    @RequestMapping("login")
    public void login(HttpServletResponse response, HttpSession session, String username, String password) {
        XUser xUser = xUserService.login(username, password);
        if (xUser != null) {
            session.setAttribute("BACKSYS_USER", xUser);

            ResponseUtil.ajaxReturn(response, "", "登录成功", 1);
        } else {
            ResponseUtil.ajaxReturn(response, "", "登录失败", 0);
        }
    }
}
