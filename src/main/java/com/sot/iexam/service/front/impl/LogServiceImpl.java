package com.sot.iexam.service.front.impl;

import com.github.pagehelper.PageHelper;
import com.sot.iexam.DAO.jpa.XUserRepository;
import com.sot.iexam.DAO.mybatis.mapper.examineesMapper;
import com.sot.iexam.DAO.mybatis.mapper.logMapper;
import com.sot.iexam.DO.XUser;
import com.sot.iexam.DO.log;
import com.sot.iexam.service.front.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    logMapper logMapper;
    @Autowired
    XUserRepository xUserRepository;
    @Autowired
    examineesMapper examineesMapper;

    @Override
    public void insert(log log) {
        logMapper.insert(log);
    }

    @Override
    public Map getLoglist(Integer page,Integer size,Integer type) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        PageHelper.startPage(page,size);

        List<log> logList=logMapper.getLogListByType(type);
        Integer count=logMapper.getLogListByTypeCount(type);
        Map m=new HashMap();
        for (log l:logList) {
            if (l.getOperateType()==1){
                l.setOperateUserName(xUserRepository.findByIdToNews(l.getOperateUser()).getRealName());
            }else if (l.getOperateType()==0){
                l.setOperateUserName(examineesMapper.getExamineesInfoById(l.getOperateUser()).getName());
            }
        }
        m.put("data",logList);
        m.put("count",count);
        return m;
    }

}
    