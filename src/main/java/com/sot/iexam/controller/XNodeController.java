package com.sot.iexam.controller;

import com.sot.iexam.DO.XNode;
import com.sot.iexam.annotation.LogAnnotation;
import com.sot.iexam.service.back.XNodeService;
import com.sot.iexam.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


/**
 * @author Kimbobo
 */
@Controller
@RequestMapping("xNode")
public class XNodeController {

    @Autowired
    XNodeService xNodeService;

    @LogAnnotation(needData = false, operateType = "添加权限权限")
    @RequestMapping("insert")
    public void insert(HttpServletResponse response, @RequestBody XNode xNode) {
        XNode node = xNodeService.insert(xNode);
        ResponseUtil.ajaxReturn(response, node, "此权限信息 插入完毕", 1);
    }

    @LogAnnotation(needData = false, operateType = "删除权限权限")
    @RequestMapping("deleteRestful/{id}")
    @Transactional
    public void deleteRestful(HttpServletResponse response, @PathVariable("id") int id) {
        xNodeService.deleteRestful(id);
        ResponseUtil.ajaxReturn(response, "", "此权限信息 已清除", 1);
    }

    @LogAnnotation(needData = false, operateType = "删除权限权限")
    @RequestMapping("delete")
    @Transactional
    public void delete(HttpServletResponse response, int id) {
        xNodeService.delete(id);
        ResponseUtil.ajaxReturn(response, "", "此权限信息 已清除", 1);
    }

    @LogAnnotation(needData = false, operateType = "删除权限权限")
    @RequestMapping("deleteQuery")
    @Transactional
    public void deleteQuery(HttpServletResponse response, int ids[]) {
        xNodeService.deleteQuery(ids);
        ResponseUtil.ajaxReturn(response, "", "选中的权限信息 已清除", 1);
    }

    @LogAnnotation(needData = false, operateType = "修改权限信息")
    @RequestMapping("modify")
    public void modify(HttpServletResponse response, @RequestBody XNode xNode) {
        XNode modified = xNodeService.modify(xNode);
        if (modified != null)
            ResponseUtil.ajaxReturn(response, modified, "此权限信息 修改完毕", 1);
        else ResponseUtil.ajaxReturn(response, modified, "此权限信息 不存在", 0);
    }


    @RequestMapping("find")
    public void find(HttpServletResponse response, String email, String iconClass, String isMenu, String isShow, String level, String name, String pid, String sortId, String url, String id, String direction, String orderBy, String limitPage, String limitSize) {

        List<XNode> xNodes = xNodeService.find(email, iconClass, isMenu, isShow, level, name, pid, sortId, url, id, direction, orderBy, limitPage, limitSize);
        ResponseUtil.ajaxReturn(response, xNodes, "此权限信息 查找完毕", 1);
    }

    @RequestMapping("findForTable")
    public void findForTable(HttpServletResponse response, String email, String iconClass, String isMenu, String isShow, String level, String name, String pid, String sortId, String url, String id, String direction, String orderBy, String limitPage, String limitSize) {

        Map map = xNodeService.findForTable(email, iconClass, isMenu, isShow, level, name, pid, sortId, url, id, direction, orderBy, limitPage, limitSize);

        ResponseUtil.ajaxReturn(response, map);
    }

    @RequestMapping("findForColumns")
    public void findForColumns(HttpServletResponse response) throws IllegalAccessException {
        XNode column = xNodeService.findForColumns();
        ResponseUtil.ajaxReturn(response, column);
    }

    @RequestMapping("findNodeToBack")
    public void findNodeToBack(HttpServletResponse response) {
        List<XNode> xNodes = xNodeService.findNodeToBack();
        ResponseUtil.ajaxReturn(response, xNodes, "1", 1);
    }

    @RequestMapping("findById")
    public void findById(HttpServletResponse response, int id) {
        ResponseUtil.ajaxReturn(response, xNodeService.findById(id));
    }

    @RequestMapping("changeOrder")
    public void changeOrder(HttpServletResponse response, int startId, int targetId) {
        if (xNodeService.ChangeOrder(startId, targetId)) {
            ResponseUtil.ajaxReturn(response, "", "处理完毕", 1);
        } else {

        }
    }

    @RequestMapping("getNodesByUserId")
    public void getNodesByUserId(HttpServletResponse response, int userId) {
        ResponseUtil.ajaxReturn(response, xNodeService.getNodesByUserId(userId));
    }

    @RequestMapping("getNodesByRoleId")
    public void getNodesByRoleId(HttpServletResponse response, int roleId) {
        ResponseUtil.ajaxReturn(response, xNodeService.getNodesByRoleId(roleId));
    }
}
