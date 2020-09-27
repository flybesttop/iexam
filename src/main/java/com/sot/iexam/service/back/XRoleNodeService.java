package com.sot.iexam.service.back;

import com.sot.iexam.DO.XRoleNode;

import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * @author Kimbobo
 */
public interface XRoleNodeService {

    /**
     * 插入 角色节点关系
     *
     * @param xRoleNode 实体类
     *                  nodeId:,
     *                  roleId:,
     */
    void insert(XRoleNode xRoleNode);

    /**
     * 删除 角色节点关系，根据记录id删除
     *
     * @param id 记录id
     */
    void deleteById(int id);

    /**
     * 传入实体类更新 角色节点关系
     *
     * @param xRoleNode 实体类
     *                  id:,
     *                  nodeId:,
     *                  roleId:,
     */
    void modify(XRoleNode xRoleNode);

    /**
     * 多条件(输入以下多个条件)
     * 单条件查找（输入以下一个条件）
     * 全查找（不输入条件）
     * 注： limitPage 必须  limitSize 必须 否则分页就是limit 0,10
     * 角色节点关系
     *
     * @param nodeId
     * @param roleId
     * @param direction asc desc
     * @param orderBy   order by XXX 根据表的什么顺序进行排序
     * @param limitPage 分页起页 第0页开始
     * @param limitSize 分页大小
     * @return
     */
    List<XRoleNode> find(String nodeId, String roleId, String id,
                         String direction, String orderBy, String limitPage, String limitSize);

    Map findForTable(String nodeId, String roleId, String id,
                     String direction, String orderBy, String limitPage, String limitSize);

    XRoleNode findForColumns();

    void batchModify(XRoleNode[] xRoleNodes);

    Set<Integer> findWithRoleIds(int[] roleIds);

    void deleteByRoleId(int roleId);
}
