package com.sot.iexam.service.back.impl;

import com.sot.iexam.DO.XDictionary;
import com.sot.iexam.service.back.XDictionaryService;
import com.sot.iexam.DAO.jpa.XDictionaryRepository;
import com.sot.iexam.util.ClassBuilder;
import com.sot.iexam.util.SqlUtil;
import com.sot.iexam.util.Timmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * @author Kimbobo
 */
@Service
public class XDictionaryServiceImpl implements XDictionaryService {

    @Autowired
    XDictionaryRepository xDictionaryRepository;

    @Override
    public void insert(XDictionary emoDictionary) {
        emoDictionary.setCreateTime(String.valueOf(Timmer.timeLong()));
        xDictionaryRepository.save(emoDictionary);
    }

    @Override
    public void deleteById(int id) {
        xDictionaryRepository.deleteById(id);
    }

    @Override
    public void modify(XDictionary emoDictionary) {
        int id = emoDictionary.getId();
        Optional<XDictionary> temp = xDictionaryRepository.findById(id);
        //查出的作为基准(后者) 两者进行比较对于传入的属性不为空的进行修改
        XDictionary emoDictionary1 = null;
        if (temp.isPresent()) {
            emoDictionary1 = temp.get();
            try {
                ClassBuilder.Concat(emoDictionary, emoDictionary1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            emoDictionary1.setModifiedTime(String.valueOf(Timmer.timeLong()));
            xDictionaryRepository.save(emoDictionary1);
        }
    }

    @Override
    public List<XDictionary> find(String createTimeStart, String createTimeEnd, String modifiedTimeStart, String modifiedTimeEnd,
                                  String code, String value, String type, String detail, String id,
                                  String direction, String orderBy, String limitPage, String limitSize) {
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
        List<XDictionary> emoDictionarys = xDictionaryRepository.find(
                createTimeStart, createTimeEnd, modifiedTimeStart, modifiedTimeEnd,
                code, value, type, detail, id,
                new PageRequest(Integer.valueOf(limitPage), Integer.valueOf(limitSize), sort));
        return emoDictionarys;
    }

    @Override
    public Map findForTable(String createTimeStart, String createTimeEnd, String modifiedTimeStart, String modifiedTimeEnd,
                            String code, String value, String type, String detail, String id,
                            String direction, String orderBy, String limitPage, String limitSize) {
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
        List<XDictionary> xRoles = xDictionaryRepository.find(
                createTimeStart, createTimeEnd, modifiedTimeStart, modifiedTimeEnd,
                code, value, type, detail, id,
                new PageRequest(Integer.valueOf(limitPage), Integer.valueOf(limitSize), sort));
        Long count = xDictionaryRepository.Count(createTimeStart, createTimeEnd, modifiedTimeStart, modifiedTimeEnd,
                code, value, type, detail, id);
        Map map = new HashMap();
        map.put("rows", xRoles);
        map.put("total", count);
        return map;
    }

    @Override
    public XDictionary findForColumns() {

        XDictionary column = new XDictionary();
        try {
            column = (XDictionary) ClassBuilder.setNullToDefault(column);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return column;
    }

    @Override
    public List<XDictionary> findDataBasePre(String code, String type) {
        return xDictionaryRepository.findDataBasePre(code, type);
    }

    @Override
    public void changeFormBack(XDictionary[] XDictionaries) {
        for (int i = 0; i < XDictionaries.length; i++) {
            XDictionary dictionary = XDictionaries[i];
            System.out.println(dictionary);
            xDictionaryRepository.save(dictionary);
        }
    }
}
