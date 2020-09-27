package com.sot.iexam.controller;

import com.sot.iexam.DO.XUserRole;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.back.impl.XUserRoleServiceImpl;
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
@RequestMapping("xUserRole")
public class XUserRoleController {
    @Autowired
    XUserRoleServiceImpl xUserRoleService;

    @LogAnnotation(needData = false, operateType = "添加用户角色信息")
    @RequestMapping("insert")
    public void insert(HttpServletResponse response, @RequestBody XUserRole xUserRole) {
        xUserRoleService.insert(xUserRole);
        ResponseUtil.ajaxReturn(response, xUserRole, "此用户角色联系 插入完毕", 1);
    }

    @LogAnnotation(needData = false, operateType = "添加用户角色信息")
    @RequestMapping("insertBatchWithDelete")
    public void insertBatchWithDelete(HttpServletResponse response, @RequestBody XUserRole[] xUserRole) throws IllegalAccessException {
        xUserRoleService.insertBatchWithDelete(xUserRole);
        ResponseUtil.ajaxReturn(response, xUserRole, "用户角色关系更新完毕 刷新主页面后生效", 1);
    }

    @LogAnnotation(needData = false, operateType = "删除用户角色信息")
    @RequestMapping("deleteRestful/{id}")
    public void deleteRestful(HttpServletResponse response, @PathVariable("id") int id) {
        xUserRoleService.deleteById(id);
        ResponseUtil.ajaxReturn(response, "", "此用户角色联系 已清除", 1);
    }

    @LogAnnotation(needData = false, operateType = "删除用户角色信息")
    @RequestMapping("delete")
    public void delete(HttpServletResponse response, int id) {
        xUserRoleService.deleteById(id);
        ResponseUtil.ajaxReturn(response, "", "此用户角色联系 已清除", 1);
    }

    @LogAnnotation(needData = false, operateType = "修改用户角色信息")
    @RequestMapping("modify")
    public void modify(HttpServletResponse response, @RequestBody XUserRole xUserRole) {
        xUserRoleService.insert(xUserRole);
        ResponseUtil.ajaxReturn(response, xUserRole, "此用户角色联系 修改完毕", 1);
    }

    @RequestMapping("find")
    public void find(HttpServletResponse response,
                     String roleId, String userId, String id,
                     String direction, String orderBy, String limitPage, String limitSize) {
        List<XUserRole> xUserRoles = xUserRoleService.find(
                roleId, userId, id,
                direction, orderBy, limitPage, limitSize);
        ResponseUtil.ajaxReturn(response, xUserRoles, "此用户角色联系 查找完毕", 1);
    }

    @RequestMapping("findForTable")
    public void findForTable(HttpServletResponse response,
                             String roleId, String userId, String id,
                             String direction, String orderBy, String limitPage, String limitSize) {
        Map map = xUserRoleService.findForTable(
                roleId, userId, id,
                direction, orderBy, limitPage, limitSize);
        ResponseUtil.ajaxReturn(response, map);
    }

    @RequestMapping("findForColumns")
    public void findForColumns(HttpServletResponse response) throws IllegalAccessException {
        XUserRole column = xUserRoleService.findForColumns();
        ResponseUtil.ajaxReturn(response, column);
    }

    @LogAnnotation(needData = false, operateType = "查找用户角色信息")
    @RequestMapping("findByUserId")
    public void findByUserId(HttpServletResponse response, int userId) {
        List<XUserRole> xUserRoles = xUserRoleService.findByUserId(userId);
        ResponseUtil.ajaxReturn(response, xUserRoles);
    }

    @LogAnnotation(needData = false, operateType = "删除用户角色信息")
    @RequestMapping("deleteByUserId")
    public void deleteByUserId(HttpServletResponse response, int userId) {
        xUserRoleService.deleteByUserId(userId);
        ResponseUtil.ajaxReturn(response, "", "此用户角色联系 修改完毕", 1);
    }

}
