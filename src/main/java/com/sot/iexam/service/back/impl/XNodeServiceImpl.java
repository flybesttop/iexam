package com.sot.iexam.service.back.impl;

import com.sot.iexam.DO.XNode;
import com.sot.iexam.service.back.XNodeService;
import com.sot.iexam.DAO.jpa.XNodeRepository;
import com.sot.iexam.DAO.jpa.XRoleNodeRepository;
import com.sot.iexam.util.ClassBuilder;
import com.sot.iexam.util.SqlUtil;
import com.sot.iexam.util.Timmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.*;

/**
 * @author Kimbobo
 */
@Service
public class XNodeServiceImpl implements XNodeService {
    @Autowired
    XNodeRepository xNodeRepository;

    @Autowired
    XRoleNodeRepository xRoleNodeRepository;

    @Autowired
    EntityManagerFactory factory;


    @Override
    public XNode insert(XNode xNode) {
        xNode.setCreateTime(String.valueOf(Timmer.timeLong()));
        xNode.setIsMenu(0);
        xNodeRepository.save(xNode);
        Optional<XNode> xNode1 = xNodeRepository.findById(xNode.getPid());
        if (xNode1.isPresent()) {
            XNode xNode2 = xNode1.get();
            xNode2.setIsMenu(1);
            xNodeRepository.save(xNode2);
        }
        return xNode;
    }

    @Override
    public XNode insertRecord(XNode xNode) {
        xNode.setCreateTime(String.valueOf(Timmer.timeLong()));
        xNode.setIsMenu(0);
        xNode.setCanDelete((byte) 0);
        xNodeRepository.save(xNode);
        return xNode;
    }


    @Override
    public void deleteRestful(int id) {
        clearRemainNode(id);
    }

    @Override
    public void delete(int id) {
        clearRemainNode(id);
    }

    @Override
    public void deleteQuery(int[] ids) {
        for (int i = 0; i < ids.length; i++) {
            int id = ids[i];
            clearRemainNode(id);
        }
    }

    @Override
    public XNode modify(XNode node) {
        int id = node.getId();
        Optional<XNode> temp = xNodeRepository.findById(id);
        //查出的作为基准(后者) 两者进行比较对于传入的属性不为空的进行修改
        XNode node1 = null;
        if (temp.isPresent()) {
            node1 = temp.get();
            try {
                ClassBuilder.Concat(node, node1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            node1.setModifiedTime(String.valueOf(Timmer.timeLong()));
            xNodeRepository.save(node1);
        }
        return node1;
    }

    @Override
    public List<XNode> find(String email, String iconClass, String isMenu, String isShow, String level, String name, String pid, String sortId, String url, String id, String direction, String orderBy, String limitPage, String limitSize) {
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

        List<XNode> xNodes = xNodeRepository.find(email, iconClass, isMenu, isShow, level, name, pid, sortId, url, id, new PageRequest(Integer.valueOf(limitPage), Integer.valueOf(limitSize), sort));
        return xNodes;
    }

    @Override
    public Map findForTable(String email, String iconClass, String isMenu, String isShow, String level, String name, String pid, String sortId, String url, String id, String direction, String orderBy, String limitPage, String limitSize) {
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

        List<XNode> xNodes = xNodeRepository.find(email, iconClass, isMenu, isShow, level, name, pid, sortId, url, id, new PageRequest(Integer.valueOf(limitPage), Integer.valueOf(limitSize), sort));
        Long count = xNodeRepository.Count(email, iconClass, isMenu, isShow, level, name, pid, sortId, url, id);
        Map map = new HashMap();
        map.put("rows", xNodes);
        map.put("total", count);
        return map;
    }

    @Override
    public XNode findForColumns() {
        XNode column = new XNode();
        try {
            column = (XNode) ClassBuilder.setNullToDefault(column);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return column;
    }

    @Override
    public List<XNode> findNodeToBack() {
        return xNodeRepository.findNodeToBack();
    }

    @Override
    public Optional<XNode> findById(int id) {
        return xNodeRepository.findById(id);
    }

    @Override
    public boolean ChangeOrder(int startId, int tagertId) {
        Optional<XNode> start = xNodeRepository.findById(startId);
        Optional<XNode> target = xNodeRepository.findById(tagertId);
        if (start.isPresent() && target.isPresent()) {
            XNode start1 = start.get();
            XNode target1 = target.get();
            int order = start1.getSortId();
            start1.setSortId(target1.getSortId());
            target1.setSortId(order);
            xNodeRepository.save(start1);
            xNodeRepository.save(target1);
            return true;
        }
        return false;

    }

    public void clearRemainNode(int id) {
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.offer(id);
        while (!queue.isEmpty()) {
            int tempid = queue.poll();
            if (xNodeRepository.findById(tempid).isPresent()) {
                xNodeRepository.deleteById(tempid);
                xRoleNodeRepository.deleteByNodeId(tempid);
                List<XNode> xNodes = xNodeRepository.findByPid(tempid);
                for (int i = 0; i < xNodes.size(); i++) {
                    queue.offer(xNodes.get(i).getId());
                }
            }
        }
    }

    //multipart sql place

    @Override
    public List<XNode> getNodesByUserId(int userId) {
        EntityManager entityManager = factory.createEntityManager();
        List<XNode> xNodes = xNodeRepository.getNodesByUserId(userId);
        return xNodes;
    }

    @Override
    public List<XNode> getNodesByRoleId(int roleId) {
        EntityManager entityManager = factory.createEntityManager();
        List<XNode> xNodes = xNodeRepository.getNodesByRoleId(roleId);
        return xNodes;
    }

    @Override
    public List<XNode> selectAll() {
        return xNodeRepository.selectAll();
    }
}
