package com.sot.iexam.service.back;


import com.sot.iexam.DO.XDictionary;

import java.util.List;
import java.util.Map;

/**
 * @author Kimbobo
 */
public interface XDictionaryService {
    /**
     * 插入 字典信息
     *
     * @param emoDictionary 实体类
     *                      createTime:,
     *                      modifiedTime:,
     *                      code:,
     *                      value:
     *                      type:
     */
    void insert(XDictionary emoDictionary);

    /**
     * 删除 字典信息，根据记录id删除
     *
     * @param id 记录id
     */
    void deleteById(int id);

    /**
     * 传入实体类更新 字典信息
     *
     * @param emoDictionary 实体类
     *                      id:,
     *                      createTime:,
     *                      modifiedTime:,
     *                      code:,
     *                      value:
     *                      type:
     */
    void modify(XDictionary emoDictionary);

    /**
     * 多条件(输入以下多个条件)
     * 单条件查找（输入以下一个条件）
     * 全查找（不输入条件）
     * 注： limitPage 必须  limitSize 必须 否则分页就是limit 0,10
     * 字典信息
     *
     * @param createTimeStart   添加时间起
     * @param createTimeEnd     添加时间末
     * @param modifiedTimeStart 修改实际起
     * @param modifiedTimeEnd   修改时间末
     * @param code              字典键
     * @param value             字典值
     * @param type              字典段类型
     * @param detail            详细信息
     * @param direction         asc desc
     * @param orderBy           order by XXX 根据表的什么顺序进行排序
     * @param limitPage         分页起页 第0页开始
     * @param limitSize         分页大小
     * @return
     */
    List<XDictionary> find(String createTimeStart, String createTimeEnd, String modifiedTimeStart, String modifiedTimeEnd,
                           String code, String value, String type, String detail, String id,
                           String direction, String orderBy, String limitPage, String limitSize);

    Map findForTable(String createTimeStart, String createTimeEnd, String modifiedTimeStart, String modifiedTimeEnd,
                     String code, String value, String type, String detail, String id,
                     String direction, String orderBy, String limitPage, String limitSize);

    XDictionary findForColumns();

    /**
     * 前台列表情况
     */
    List<XDictionary> findDataBasePre(String code, String type);

    /**
     * 前台列表情况修改
     */
    void changeFormBack(XDictionary[] XDictionaries);
}
