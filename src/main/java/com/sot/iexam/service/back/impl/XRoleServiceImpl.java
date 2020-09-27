package com.sot.iexam.service.back.impl;

import com.sot.iexam.DAO.jpa.XUserRoleRepository;
import com.sot.iexam.DAO.mybatis.mapper.invigilatorMapper;
import com.sot.iexam.DAO.mybatis.mapper.reviewerMapper;
import com.sot.iexam.DO.XRole;
import com.sot.iexam.DO.XUserRole;
import com.sot.iexam.DO.invigilator;
import com.sot.iexam.service.back.XRoleService;
import com.sot.iexam.DAO.jpa.XRoleRepository;
import com.sot.iexam.util.ClassBuilder;
import com.sot.iexam.util.SqlUtil;
import com.sot.iexam.util.Timmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * @author Kimbobo
 */
@Service
public class XRoleServiceImpl implements XRoleService {
    @Autowired
    XRoleRepository xRoleRepository;
    @Autowired
    XUserRoleRepository xUserRoleRepository;
    @Autowired
    EntityManagerFactory factory;
    @Autowired
    invigilatorMapper invigilatorMapper;
    @Autowired
    reviewerMapper reviewerMapper;

    @Override
    public void insert(XRole xRole) {
        xRole.setCreateTime(String.valueOf(Timmer.timeLong()));
        xRole.setModifiedTime(String.valueOf(Timmer.timeLong()));
        if (xRole.getRemark() == null)
            xRole.setRemark("-1");
        xRoleRepository.save(xRole);
    }

    @Override
    public void deleteById(int id) {
        xRoleRepository.deleteById(id);
    }

    @Override
    public void modify(XRole role) {
        int id = role.getId();
        Optional<XRole> temp = xRoleRepository.findById(id);
        //查出的作为基准(后者) 两者进行比较对于传入的属性不为空的进行修改
        XRole role1 = null;
        if (temp.isPresent()) {
            role1 = temp.get();
            try {
                ClassBuilder.Concat(role, role1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            role1.setModifiedTime(String.valueOf(Timmer.timeLong()));
            xRoleRepository.save(role1);
        }
    }

    @Override
    public List<XRole> find(String name, String remark, String status, String id, String direction, String orderBy, String limitPage, String limitSize) {
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
        List<XRole> xRoles = xRoleRepository.find(name, remark, status, id, new PageRequest(Integer.valueOf(limitPage), Integer.valueOf(limitSize), sort));
        return xRoles;
    }

    @Override
    public Map findForTable(String name, String remark, String status, String id, String direction, String orderBy, String limitPage, String limitSize) {
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
        List<XRole> xRoles = xRoleRepository.find(name, remark, status, id, new PageRequest(Integer.valueOf(limitPage), Integer.valueOf(limitSize), sort));
        Long count = xRoleRepository.Count(name, remark, status, id);
        Map map = new HashMap();
        map.put("rows", xRoles);
        map.put("total", count);
        return map;
    }

    @Override
    public XRole findForColumns() {
        XRole column = new XRole();
        try {
            column = (XRole) ClassBuilder.setNullToDefault(column);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return column;
    }

    @Override
    public List<XRole> findAll() {
        List<XRole> xRoles = xRoleRepository.findAll();
        return xRoles;
    }

    //multipart sql
    public List<XRole> getRolesByUserId(int userId) {
        EntityManager manager = factory.createEntityManager();
        List<XRole> xRoles = xRoleRepository.getRolesByUserId(userId);
        return xRoles;
    }

    public void removeUser(Integer userId, Integer roleId, String code) {
        if (code.equals("1000")) {//修改阅卷
            reviewerMapper.disableReviewer(userId);
        } else if (code.equals("1001")) {//修改监考
            invigilatorMapper.disableInvigilatorMapper(userId);
        }
        xUserRoleRepository.deleteByUserIdAndRoleId(userId, roleId);
    }

    @Override
    public void addUser(Integer userId, Integer roleId, String code) {
        if (code.equals("1000")) {//添加阅卷
            Integer count = reviewerMapper.getReviewerAssignment(userId);
            if (count > 0) {
                reviewerMapper.enableReviewer(userId);
            }

        } else if (code.equals("1001")) {//添加监考
            Integer count = invigilatorMapper.getInvigilatorAssignment(userId);
            if (count > 0) {
                invigilatorMapper.enableInvigilatorMapper(userId);
            } else {
                invigilator invigilator = new invigilator();
                invigilator.setInvigilatorId(userId);
                invigilator.setStatus(1);
                invigilatorMapper.insert(invigilator);
            }

        }
        XUserRole userRole = new XUserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(userId);
        xUserRoleRepository.save(userRole);
    }
}
