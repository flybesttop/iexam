package com.sot.iexam.service.back.impl;

import com.sot.iexam.DO.XRoleNode;
import com.sot.iexam.service.back.XRoleNodeService;
import com.sot.iexam.DAO.jpa.XRoleNodeRepository;
import com.sot.iexam.util.ClassBuilder;
import com.sot.iexam.util.SqlUtil;
import com.sot.iexam.util.Timmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
/**
 * @author Kimbobo
 */
@Service
public class XRoleNodeServiceImpl implements XRoleNodeService {

    @Autowired
    XRoleNodeRepository jpaXRoleNode;

    @Override
    public void insert(XRoleNode xRoleNode) {
        xRoleNode.setCreateTime(String.valueOf(Timmer.timeLong()));
        xRoleNode.setModifiedTime(String.valueOf(Timmer.timeLong()));
        jpaXRoleNode.save(xRoleNode);
    }

    @Override
    public void deleteById(int id) {
        jpaXRoleNode.deleteById(id);
    }

    @Override
    public void modify(XRoleNode xRoleNode) {
        int id = xRoleNode.getId();
        Optional<XRoleNode> temp = jpaXRoleNode.findById(id);
        XRoleNode node1 = null;
        if (temp.isPresent()) {
            node1 = temp.get();
            try {
                ClassBuilder.Concat(xRoleNode, temp);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            node1.setModifiedTime(String.valueOf(Timmer.timeLong()));
        }
        jpaXRoleNode.save(xRoleNode);
    }

    @Override
    public List<XRoleNode> find(String nodeId, String roleId, String id, String direction, String orderBy, String limitPage, String limitSize) {
        if (limitPage == "" || limitPage == null) {
            limitPage = "0";
        }
        if (limitSize == "" || limitSize == null) {
            limitSize = "10";
        }
        if (orderBy == "" || orderBy == null) {
            orderBy = "id";
        }
        Sort sort = SqlUtil.getSort(direction, orderBy);
        List<XRoleNode> xRoleNodes = jpaXRoleNode.find(nodeId, roleId, id, new PageRequest(Integer.valueOf(limitPage), Integer.valueOf(limitSize), sort));
        return xRoleNodes;
    }

    @Override
    public Map findForTable(String nodeId, String roleId, String id, String direction, String orderBy, String limitPage, String limitSize) {
        if (limitPage == "" || limitPage == null) {
            limitPage = "0";
        }
        if (limitSize == "" || limitSize == null) {
            limitSize = "10";
        }
        if (orderBy == "" || orderBy == null) {
            orderBy = "id";
        }
        Sort sort = SqlUtil.getSort(direction, orderBy);
        List<XRoleNode> xRoles = jpaXRoleNode.find(nodeId, roleId, id, new PageRequest(Integer.valueOf(limitPage), Integer.valueOf(limitSize), sort));
        Long count = jpaXRoleNode.Count(nodeId, roleId, id);
        Map map = new HashMap();
        map.put("rows", xRoles);
        map.put("total", count);
        return map;
    }

    @Override
    public XRoleNode findForColumns() {
        XRoleNode column = new XRoleNode();
        try {
            column = (XRoleNode) ClassBuilder.setNullToDefault(column);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return column;
    }

    @Override
    @Transactional
    public void batchModify(XRoleNode[] xRoleNodes) {
        if (xRoleNodes.length != 0) {
            jpaXRoleNode.deleteByRoleId(xRoleNodes[0].getRoleId());
            for (int i = 0; i < xRoleNodes.length; i++) {
                jpaXRoleNode.save(xRoleNodes[i]);
            }
        }
    }

    @Override
    public Set<Integer> findWithRoleIds(int[] roleIds) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < roleIds.length; i++) {
            int[] xRoleNodes = jpaXRoleNode.selectByRoleId(roleIds[i]);
            for (int j = 0; j < xRoleNodes.length; j++) {
                set.add(xRoleNodes[j]);
            }
        }
        return set;
    }

    @Override
    @Transactional
    public void deleteByRoleId(int roleId) {
        jpaXRoleNode.deleteByRoleId(roleId);
    }

}
