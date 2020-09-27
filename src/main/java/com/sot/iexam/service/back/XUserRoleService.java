package com.sot.iexam.service.back;


import com.sot.iexam.DO.XUserRole;

import java.util.List;
import java.util.Map;

/**
 * @author Kimbobo
 */
public interface XUserRoleService {

    /**
     * 插入 用户角色联系
     *
     * @param xUserRole 实体类
     *                  roleId:,
     *                  userId:
     */
    void insert(XUserRole xUserRole);

    /**
     * 删除 用户角色联系，根据记录id删除
     *
     * @param id 记录id
     */
    void deleteById(int id);

    /**
     * 传入实体类更新 用户角色联系
     *
     * @param xUserRole 实体类
     *                  id:,
     *                  roleId:,
     *                  userId:
     */
    void modify(XUserRole xUserRole);

    /**
     * 多条件(输入以下多个条件)
     * 单条件查找（输入以下一个条件）
     * 全查找（不输入条件）
     * 注： limitPage 必须  limitSize 必须 否则分页就是limit 0,10
     * 用户角色联系
     *
     * @param roleId
     * @param userId
     * @param id
     * @param direction asc desc
     * @param orderBy   order by XXX 根据表的什么顺序进行排序
     * @param limitPage 分页起页 第0页开始
     * @param limitSize 分页大小
     * @return
     */
    List<XUserRole> find(String roleId, String userId, String id,
                         String direction, String orderBy, String limitPage, String limitSize);

    Map findForTable(String roleId, String userId, String id,
                     String direction, String orderBy, String limitPage, String limitSize);

    XUserRole findForColumns();

     List<XUserRole> findByUserId(int userId);

    void insertBatchWithDelete(XUserRole[] xUserRole) throws IllegalAccessException;

    void deleteByUserId(int userId);
}
