package com.sot.iexam.service.back.impl;


import com.sot.iexam.DO.XUserRole;
import com.sot.iexam.service.back.XUserRoleService;
import com.sot.iexam.DAO.jpa.XUserRoleRepository;
import com.sot.iexam.util.ClassBuilder;
import com.sot.iexam.util.SqlUtil;
import com.sot.iexam.util.Timmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * @author Kimbobo
 */
@Service
public class XUserRoleServiceImpl implements XUserRoleService {

    @Autowired
    XUserRoleRepository xUserRoleRepository;

    @Override
    public void insert(XUserRole xUserRole) {
        xUserRole.setCreateTime(String.valueOf(Timmer.timeLong()));
        xUserRoleRepository.save(xUserRole);
    }

    @Override
    public void deleteById(int id) {
        xUserRoleRepository.deleteById(id);
    }

    @Override
    public void modify(XUserRole xUserRole) {
        int id = xUserRole.getId();
        Optional<XUserRole> temp = xUserRoleRepository.findById(id);
        //查出的作为基准(后者) 两者进行比较对于传入的属性不为空的进行修改
        XUserRole xUserRole1 = null;
        if (temp.isPresent()) {
            xUserRole1 = temp.get();
            try {
                ClassBuilder.Concat(xUserRole, xUserRole1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            xUserRole1.setModifiedTime(String.valueOf(Timmer.timeLong()));
            xUserRoleRepository.save(xUserRole1);
        }
    }

    @Override
    public List<XUserRole> find(String nodeId, String roleId, String id, String direction, String orderBy, String limitPage, String limitSize) {
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
        List<XUserRole> xUserRoles = xUserRoleRepository.find(nodeId, roleId, id, new PageRequest(Integer.valueOf(limitPage), Integer.valueOf(limitSize), sort));
        return xUserRoles;
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
        List<XUserRole> xRoles = xUserRoleRepository.find(nodeId, roleId, id, new PageRequest(Integer.valueOf(limitPage), Integer.valueOf(limitSize), sort));
        Long count = xUserRoleRepository.Count(nodeId, roleId, id);
        Map map = new HashMap();
        map.put("rows", xRoles);
        map.put("total", count);
        return map;
    }

    @Override
    public XUserRole findForColumns() {
        XUserRole column = new XUserRole();
        try {
            column = (XUserRole) ClassBuilder.setNullToDefault(column);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return column;
    }

    public List<XUserRole> findByUserId(int userId) {
        return xUserRoleRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void insertBatchWithDelete(XUserRole[] xUserRole) throws IllegalAccessException {
        if (xUserRole.length != 0) {
            xUserRoleRepository.deleteByUserId(xUserRole[0].getUserId());
            for (int i = 0; i < xUserRole.length; i++) {
                xUserRoleRepository.save(xUserRole[i]);
            }
        }
    }

    @Override
    public void deleteByUserId(int userId) {
        xUserRoleRepository.deleteByUserId(userId);
    }
}
