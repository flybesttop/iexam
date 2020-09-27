package com.sot.iexam.service.back;

import com.sot.iexam.DO.XRole;

import java.util.List;
import java.util.Map;

/**
 * @author Kimbobo
 */
public interface XRoleService {

    /**
     * 插入 角色信息
     *
     * @param xRole    实体类
     *                 name:,
     *                 remark:,
     *                 status:
     */
    void insert(XRole xRole);

    /**
     * 删除 角色信息，根据记录id删除
     *
     * @param id       记录id
     */
    void deleteById(int id);

    /**
     * 传入实体类更新 角色信息
     *
     * @param xRole    实体类
     *                 id:,
     *                 name:,
     *                 remark:,
     *                 status:
     */
    void modify(XRole xRole);

    /**
     * 多条件(输入以下多个条件)
     * 单条件查找（输入以下一个条件）
     * 全查找（不输入条件）
     * 注： limitPage 必须  limitSize 必须 否则分页就是limit 0,10
     * 角色信息
     *
     * @param name
     * @param remark
     * @param status
     * @param orderBy   order by XXX 根据表的什么顺序进行排序
     * @param direction asc desc
     * @param limitPage 分页起页 第0页开始
     * @param limitSize 分页大小
     * @return
     */
    List<XRole> find(String name, String remark, String status, String id,
                     String direction, String orderBy, String limitPage, String limitSize);

    Map findForTable(String name, String remark, String status, String id,
                     String direction, String orderBy, String limitPage, String limitSize);

    XRole findForColumns();

    List<XRole> findAll();

    List<XRole> getRolesByUserId(int userId);

    void removeUser(Integer userId, Integer roleId, String code);

    void addUser(Integer userId, Integer roleId, String code);
}
