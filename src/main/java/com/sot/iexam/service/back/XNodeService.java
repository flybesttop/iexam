package com.sot.iexam.service.back;

import com.sot.iexam.DO.XNode;

import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * @author Kimbobo
 */
public interface XNodeService {


    /**
     * 插入 节点信息
     *
     * @param xNode 实体类
     *              email;,
     *              iconClass;,
     *              isMenu;,
     *              isShow;,
     *              level;,
     *              name;,
     *              pid;,
     *              sortId;,
     *              url;
     */
    XNode insertRecord(XNode xNode);

    XNode insert(XNode xNode);

    /**
     * 删除 节点信息，根据记录id删除
     *
     * @param id 记录id
     */
    void deleteRestful(int id);

    /**
     * 删除 节点信息，根据记录id删除
     *
     * @param id 记录id
     */
    void delete(int id);

    void deleteQuery(int[] ids);

    /**
     * 传入实体类更新 节点信息
     *
     * @param xNode 实体类
     *              id:,
     *              email;,
     *              iconClass;,
     *              isMenu;,
     *              isShow;,
     *              level;,
     *              name;,
     *              pid;,
     *              sortId;,
     *              url;
     */
    XNode modify(XNode xNode);

    /**
     * 多条件(输入以下多个条件)
     * 单条件查找（输入以下一个条件）
     * 全查找（不输入条件）
     * 注： limitPage 必须  limitSize 必须 否则分页就是limit 0,10
     * 节点信息
     *
     * @param email
     * @param iconClass icon
     * @param isMenu    是否菜单
     * @param isShow    是否显示
     * @param level     节点等级
     * @param name      节点名
     * @param pid       父id
     * @param sortId    排序
     * @param url       访问地址
     * @param orderBy   order by XXX 根据表的什么顺序进行排序
     * @param direction asc   desc
     * @param limitPage 分页起页 第0页开始
     * @param limitSize 分页大小
     * @return
     */
    List<XNode> find(String email, String iconClass, String isMenu, String isShow, String level, String name, String pid, String sortId, String url, String id, String direction, String orderBy, String limitPage, String limitSize);

    /**
     * 后台数据查找
     */
    Map findForTable(String email, String iconClass, String isMenu, String isShow, String level, String name, String pid, String sortId, String url, String id, String direction, String orderBy, String limitPage, String limitSize);

    /**
     * 后台列构造
     */
    XNode findForColumns();

    /**
     * 节点加载
     */
    List<XNode> findNodeToBack();

    /**
     * 根据id查找
     */
    Optional<XNode> findById(int id);

    boolean ChangeOrder(int startId, int tagertId);

    List<XNode> getNodesByUserId(int userId);

    List<XNode> getNodesByRoleId(int roleId);


    List<XNode> selectAll();
}
