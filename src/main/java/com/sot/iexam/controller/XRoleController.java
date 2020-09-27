package com.sot.iexam.controller;

import com.sot.iexam.DO.XRole;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.back.XRoleService;
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
@RequestMapping("xRole")
public class XRoleController {
    @Autowired
    XRoleService xRoleService;

    @LogAnnotation(needData = false, operateType = "删除用户角色")
    @RequestMapping("removeUser")
    public void removeUser(HttpServletResponse response, Integer userId, Integer roleId, String code) {
        xRoleService.removeUser(userId, roleId, code);
        ResponseUtil.ajaxReturn(response, null, "角色用户修改完毕", 1);
    }

    @LogAnnotation(needData = false, operateType = "添加用户角色")
    @RequestMapping("addUser")
    public void addUser(HttpServletResponse response, Integer userId, Integer roleId, String code) {
        xRoleService.addUser(userId, roleId, code);
        ResponseUtil.ajaxReturn(response, null, "角色用户修改完毕", 1);
    }

    @LogAnnotation(needData = false, operateType = "添加角色")
    @RequestMapping("insert")
    public void insert(HttpServletResponse response, @RequestBody XRole xRole) {
        xRoleService.insert(xRole);
        ResponseUtil.ajaxReturn(response, xRole, "此角色信息 插入完毕", 1);
    }

    @LogAnnotation(needData = false, operateType = "删除角色")
    @RequestMapping("deleteRestful/{id}")
    public void deleteRestful(HttpServletResponse response, @PathVariable("id") int id) {
        xRoleService.deleteById(id);
        ResponseUtil.ajaxReturn(response, "", "此角色信息 已清除", 1);
    }

    @LogAnnotation(needData = false, operateType = "删除角色")
    @RequestMapping("delete")
    public void delete(HttpServletResponse response, int id) {
        xRoleService.deleteById(id);
        ResponseUtil.ajaxReturn(response, "", "此角色信息 已清除", 1);
    }

    @LogAnnotation(needData = false, operateType = "修改角色")
    @RequestMapping("modify")
    public void modify(HttpServletResponse response, @RequestBody XRole xRole) {
        xRoleService.modify(xRole);
        ResponseUtil.ajaxReturn(response, xRole, "此角色信息 修改完毕", 1);
    }


    @RequestMapping("find")
    public void find(HttpServletResponse response,
                     String name, String remark, String status, String id,
                     String direction, String orderBy, String limitPage, String limitSize) {
        List<XRole> xRoles = xRoleService.find(
                name, remark, status, id,
                direction, orderBy, limitPage, limitSize);
        ResponseUtil.ajaxReturn(response, xRoles, "此角色信息 查找完毕", 1);
    }

    @RequestMapping("findForTable")
    public void findForTable(HttpServletResponse response,
                             String name, String remark, String status, String id,
                             String direction, String orderBy, String limitPage, String limitSize) {
        Map map = xRoleService.findForTable(
                name, remark, status, id,
                direction, orderBy, limitPage, limitSize);
        ResponseUtil.ajaxReturn(response, map);
    }

    @RequestMapping("findForColumns")
    public void findForColumns(HttpServletResponse response) throws IllegalAccessException {
        XRole column = xRoleService.findForColumns();
        ResponseUtil.ajaxReturn(response, column);
    }

    @RequestMapping("findAll")
    public void findAll(HttpServletResponse response) {
        List<XRole> xRoles = xRoleService.findAll();
        ResponseUtil.ajaxReturn(response, xRoles);
    }

    @RequestMapping("getRolesByUserId")
    public void getRolesByUserId(HttpServletResponse response, int userId) {
        ResponseUtil.ajaxReturn(response, xRoleService.getRolesByUserId(userId));
    }
}
