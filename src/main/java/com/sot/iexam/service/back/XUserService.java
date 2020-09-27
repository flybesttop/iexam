package com.sot.iexam.service.back;

import com.sot.iexam.DO.XUser;

import java.util.List;
import java.util.Map;

/**
 * @author Kimbobo
 */
public interface XUserService {
    Map getReviewByTitleId(Integer page,Integer size,String reviewSearch,Integer titleId);

    Map getAllInnerUser(Integer page,Integer size,String userSearch);

    /**
     * 插入 用户信息
     *
     * @param xUser 实体类
     *              isEnabled:,
     *              password:,
     *              phone:,
     *              realName:,
     *              username:
     */
    void insert(XUser xUser);

    /**
     * 删除 用户信息，根据记录id删除
     *
     * @param id 记录id
     */
    void deleteById(int id);

    /**
     * 传入实体类更新 用户信息
     *
     * @param xUser 实体类
     *              id:,
     *              isEnabled:,
     *              password:,
     *              phone:,
     *              realName:,
     *              username:
     */
    void modify(XUser xUser);

    /**
     * 多条件(输入以下多个条件)
     * 单条件查找（输入以下一个条件）
     * 全查找（不输入条件）
     * 注： limitPage 必须  limitSize 必须 否则分页就是limit 0,10
     * 用户信息
     *
     * @param isEnabled
     * @param password
     * @param phone
     * @param realName
     * @param username
     * @param direction asc desc
     * @param orderBy   order by XXX 根据表的什么顺序进行排序
     * @param limitPage 分页起页 第0页开始
     * @param limitSize 分页大小
     * @return
     */


    List<XUser> find(String isEnabled, String password, String phone, String realName,
                     String username, String id,
                     String direction, String orderBy, String limitPage, String limitSize);

    Map findForTable(String isEnabled, String password, String phone, String realName,
                     String username, String id,
                     String direction, String orderBy, String limitPage, String limitSize);

    XUser findForColumns();

    XUser login(String username, String password);

    List<XUser> findByRoleId(int roleID, String realName);

    List<XUser> findWithoutRoleId(int roleId, String realName);
}
