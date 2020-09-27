package com.sot.iexam.service.back.impl;

import com.github.pagehelper.PageHelper;
import com.sot.iexam.DO.XUser;
import com.sot.iexam.service.back.XUserService;
import com.sot.iexam.DAO.jpa.XUserRepository;
import com.sot.iexam.util.ClassBuilder;
import com.sot.iexam.util.SqlUtil;
import com.sot.iexam.util.Timmer;
import com.zlzkj.core.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * @author Kimbobo
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class XUserServiceImpl implements XUserService {

    @Autowired
    XUserRepository xUserRepository;

    @Override
    public Map getReviewByTitleId(Integer page, Integer size, String reviewSearch, Integer titleId) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 5;
        }

        Map<String, Object> m = new HashMap<>(8);

        List<XUser> users=xUserRepository.findByPageAndSizeByTitleId((page-1)*size,size,reviewSearch,titleId);
        m.put("data",users);
        m.put("count", xUserRepository.countByUserSearchByTitleId(reviewSearch,titleId));
        return m;
    }

    @Override
    public Map getAllInnerUser(Integer page, Integer size,String userSearch) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 5;
        }

        Map<String, Object> m = new HashMap<>(8);

        List<XUser> users=xUserRepository.findByPageAndSize((page-1)*size,size,userSearch);
        m.put("data",users);
        m.put("count", xUserRepository.countByUserSearch(userSearch));
        return m;
    }

    @Override

    public void insert(XUser xUser) {
        xUser.setCreateTime(String.valueOf(Timmer.timeLong()));

        xUserRepository.save(xUser);

    }

    @Override
    public void deleteById(int id) {
        xUserRepository.deleteById(id);
    }

    @Override
    public void modify(XUser xUser) {
        int id = xUser.getId();
        Optional<XUser> temp = xUserRepository.findById(id);
        //查出的作为基准(后者) 两者进行比较对于传入的属性不为空的进行修改
        XUser xUser1 = null;
        if (temp.isPresent()) {
            xUser1 = temp.get();
            try {
                ClassBuilder.Concat(xUser, xUser1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            xUser1.setModifiedTime(String.valueOf(Timmer.timeLong()));
            xUserRepository.save(xUser1);
        }
    }

    @Override
    public List<XUser> find(String isEnabled, String password, String phone, String realName, String username, String id, String direction, String orderBy, String limitPage, String limitSize) {
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
        List<XUser> xUsers = xUserRepository.find(isEnabled, password, phone, realName, username, id, new PageRequest(Integer.valueOf(limitPage), Integer.valueOf(limitSize), sort));
        return xUsers;
    }

    @Override
    public Map findForTable(String isEnabled, String password, String phone, String realName, String username, String id, String direction, String orderBy, String limitPage, String limitSize) {
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
        List<XUser> xRoles = xUserRepository.find(isEnabled, password, phone, realName, username, id, new PageRequest(Integer.valueOf(limitPage), Integer.valueOf(limitSize), sort));
        Long count = xUserRepository.Count(isEnabled, password, phone, realName, username, id);
        Map map = new HashMap();
        map.put("rows", xRoles);
        map.put("total", count);
        return map;
    }

    @Override
    public XUser findForColumns() {

        XUser column = new XUser();
        try {
            column = (XUser) ClassBuilder.setNullToDefault(column);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return column;
    }

    public XUser login(String account, String password) {
        XUser xUser = xUserRepository.findByUsernameAndPassword(account, password);
        if (xUser != null) {
            return xUser;
        }
        return null;
    }

    @Override
    public List<XUser> findByRoleId(int roleID, String realName) {
        return xUserRepository.findByRoleId(roleID, realName);
    }

    public List<XUser> findWithoutRoleId(int roleId, String realName) {
        return xUserRepository.findWithoutRoleId(roleId, realName);
    }


}
