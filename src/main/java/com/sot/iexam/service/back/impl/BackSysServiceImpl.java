package com.sot.iexam.service.back.impl;

import com.sot.iexam.DO.XNode;
import com.sot.iexam.DO.XRole;
import com.sot.iexam.DO.XRoleNode;
import com.sot.iexam.service.back.BackSysService;
import com.sot.iexam.DAO.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kimbobo
 */
@Service
public class BackSysServiceImpl implements BackSysService {

    @Autowired
    XRoleNodeRepository xRoleNodeRepository;
    @Autowired
    XNodeRepository xNodeRepository;
    @Autowired
    XUserRoleRepository xUserRoleRepository;
    @Autowired
    XUserRepository xUserRepository;
    @Autowired
    XRoleRepository xRoleRepository;

    @Override
    public void sysReset() {
        //节点重置(恢复到只有后台的五个节点管理，超级管理员权限，不删除已经添加的角色)
        xNodeRepository.reset();
        XNode xNode[] = new XNode[5];
        xNode[0] = new XNode().withId(1).withIsMenu(1).withLevel(1).withName("系统设置").withPid(0).withSortId(1);
        xNode[1] = new XNode().withId(2).withIsMenu(1).withLevel(2).withName("系统功能").withPid(1).withSortId(1);
        xNode[2] = new XNode().withId(3).withIsMenu(0).withLevel(3).withName("后台节点管理").withPid(2).withSortId(1).withUrl("back_nodes");
        xNode[3] = new XNode().withId(4).withIsMenu(0).withLevel(3).withName("后台角色").withPid(2).withSortId(2).withUrl("back_roles");
        xNode[4] = new XNode().withId(5).withIsMenu(0).withLevel(3).withName("后台用户").withPid(2).withSortId(3).withUrl("back_users");
        for (int i = 0; i < xNode.length; i++) {
            xNodeRepository.save(xNode[i]);
        }
        //角色节点重置
        xRoleNodeRepository.reset();
        XRole admin = xRoleRepository.getAdmin();
        XRoleNode xRoleNode[] = new XRoleNode[5];
        xRoleNode[0] = new XRoleNode().withRoleId(admin.getId()).withNodeId(1);
        xRoleNode[1] = new XRoleNode().withRoleId(admin.getId()).withNodeId(2);
        xRoleNode[2] = new XRoleNode().withRoleId(admin.getId()).withNodeId(3);
        xRoleNode[3] = new XRoleNode().withRoleId(admin.getId()).withNodeId(4);
        xRoleNode[4] = new XRoleNode().withRoleId(admin.getId()).withNodeId(5);
        for (int i = 0; i < xRoleNode.length; i++) {
            xRoleNodeRepository.save(xRoleNode[i]);
        }
    }

    @Override
    public void backUp() {
        //数据库备份
    }

    @Override
    public void importBackUp() {
        //数据库备份恢复 考虑导入文件
    }
}
