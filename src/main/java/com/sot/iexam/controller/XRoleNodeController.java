package com.sot.iexam.controller;

import com.sot.iexam.DO.XRoleNode;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.back.XRoleNodeService;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author Kimbobo
 */
@Controller
@RequestMapping("xRoleNode")
public class XRoleNodeController {
    @Autowired
    XRoleNodeService xRoleNodeService;

    @LogAnnotation(needData = false, operateType = "添加角色权限关系")
    @RequestMapping("insert")
    public void insert(HttpServletResponse response, @RequestBody XRoleNode xRoleNode) {
        xRoleNodeService.insert(xRoleNode);
        ResponseUtil.ajaxReturn(response, xRoleNode, "此角色节点关系 插入完毕", 1);
    }

    @LogAnnotation(needData = false, operateType = "删除角色权限关系")
    @RequestMapping("deleteRestful/{id}")
    public void deleteRestful(HttpServletResponse response, @PathVariable("id") int id) {
        xRoleNodeService.deleteById(id);
        ResponseUtil.ajaxReturn(response, "", "此角色节点关系 已清除", 1);
    }

    @LogAnnotation(needData = false, operateType = "删除角色权限关系")
    @RequestMapping("delete")
    public void delete(HttpServletResponse response, int id) {
        xRoleNodeService.deleteById(id);
        ResponseUtil.ajaxReturn(response, "", "此角色节点关系 已清除", 1);
    }

    @LogAnnotation(needData = false, operateType = "删修改角色权限关系")
    @RequestMapping("modify")
    public void modify(HttpServletResponse response, @RequestBody XRoleNode xRoleNode) {
        xRoleNodeService.modify(xRoleNode);
        ResponseUtil.ajaxReturn(response, xRoleNode, "此角色节点关系 修改完毕", 1);
    }


    @RequestMapping("find")
    public void find(HttpServletResponse response,
                     String nodeId, String roleId, String id,
                     String direction, String orderBy, String limitPage, String limitSize) {
        List<XRoleNode> xRoleNodes = xRoleNodeService.find(
                nodeId, roleId, id,
                direction, orderBy, limitPage, limitSize);
        ResponseUtil.ajaxReturn(response, xRoleNodes, "此角色节点关系 查找完毕", 1);
    }


    @RequestMapping("findForTable")
    public void findForTable(HttpServletResponse response,
                             String nodeId, String roleId, String id,
                             String direction, String orderBy, String limitPage, String limitSize) {
        Map map = xRoleNodeService.findForTable(nodeId, roleId, id,
                direction, orderBy, limitPage, limitSize);
        ResponseUtil.ajaxReturn(response, map);
    }


    @RequestMapping("findForColumns")
    public void findForColumns(HttpServletResponse response) throws IllegalAccessException {
        XRoleNode column = xRoleNodeService.findForColumns();
        ResponseUtil.ajaxReturn(response, column);
    }

    @LogAnnotation(needData = false, operateType = "批量修改角色权限")
    @RequestMapping("batchModify")
    public void batchModify(HttpServletResponse response, @RequestBody XRoleNode[] xRoleNodes) {
        xRoleNodeService.batchModify(xRoleNodes);
        ResponseUtil.ajaxReturn(response, "", "角色权限修改完毕", 1);
    }

    @LogAnnotation(needData = false, operateType = "根据角色id删除角色权限")
    @RequestMapping("deleteByRoleId")
    public void deleteByRoleId(HttpServletResponse response, int roleId) {
        xRoleNodeService.deleteByRoleId(roleId);
        ResponseUtil.ajaxReturn(response, "", "角色权限修改完毕", 1);
    }

    @RequestMapping("findWithRoleIds")
    public void findWithRoleIds(HttpServletResponse response, int roleIds[]) {
        Set<Integer> xRoleNodes = xRoleNodeService.findWithRoleIds(roleIds);
        ResponseUtil.ajaxReturn(response, xRoleNodes, "", 1);
    }
}
