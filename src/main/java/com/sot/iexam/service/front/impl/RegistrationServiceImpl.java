package com.sot.iexam.service.front.impl;

import com.github.pagehelper.PageHelper;
import com.sot.iexam.DAO.mybatis.mapper.examMapper;
import com.sot.iexam.DAO.mybatis.mapper.registrationMapper;
import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.registration;
import com.sot.iexam.service.front.RegistrationService;
import com.sot.iexam.util.Timmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Kimbobo
 */
@Service
@SuppressWarnings("unchecked")
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    registrationMapper registrationMapper;

    @Autowired
    examMapper examMapper;

    @Override

    public boolean addRegistration(registration registration, Map map) {

        //判断报名用

//        if (!checkExamTime(registration, map)) {
//            return false;
//        }

        if (registrationMapper.checkExistence(registration) > 0) {
            map.put("result", "已经报名过了");
            return false;
        }

        exam exam=examMapper.getExamById(registration.getExamId());
        if (exam.getNum()==exam.getCapacity()){
            map.put("result", "报名人数已满");
            return false;
        }
        exam.setNum(exam.getNum()+1);
        examMapper.update(exam);
        map.put("result", "报名成功");
        registration.setCreateTime(Timmer.now());
        registration.setModifyTime(Timmer.now());
        registrationMapper.insert(registration);
        return true;
    }

    private boolean checkExamTime(registration registration, Map map) {
        exam examInfo = examMapper.getExamById(registration.getExamId());
        if (examInfo.getRegistrationFinishTime().compareTo(Timmer.now()) < 0) {
            //报名超时了
            map.put("result", "报名已经结束");
            return false;
        }
        //如果已经有报名了

        return true;
    }

    @Override
    public Map getRegistrationByExamineesId(Integer examineesId, Integer page, Integer size) {
        Map m = new HashMap();
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        PageHelper.startPage(page, size);
        m.put("data", registrationMapper.getRegistrationByExamineesId(examineesId));
        m.put("count", registrationMapper.getCountByExamineesId(examineesId));
        return m;
    }

    @Override
    public registration getRegistrationById(Integer registrationId) {
        return registrationMapper.getRegistrationById(registrationId);
    }

    @Override
    public Map getRegistrationList(Integer page, Integer size, registration registration) {
        Map m = new HashMap();
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        PageHelper.startPage(page, size);
        m.put("data", registrationMapper.getRegistrationList(registration));
        m.put("count", registrationMapper.getCount(registration));
        return m;
    }

    @Override
    public Map getRegistrationByExamId(Integer examId, Integer page, Integer size) {
        Map m = new HashMap();
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        PageHelper.startPage(page, size);
        m.put("data", registrationMapper.getRegistrationByExamId(examId));
        m.put("count", registrationMapper.getCountByExamId(examId));
        return m;
    }

    @Override
    public boolean updateRegistration(registration registration, Map result) {
        Integer examId = registration.getExamId();
        if (!checkExamTime(registration, result)) {
            return false;
        }
        result.put("result", "报名修改成功");
        registration.setModifyTime(Timmer.now());
        registrationMapper.update(registration);
        return true;
    }

    @Override

    public boolean deleteByRegistrationId(registration registration, Map result) {
        if (!checkExamTime(registration, result)) {
            return false;
        }
        registrationMapper.deleteByRegistrationId(registration.getId());
        result.put("result", "报名取消成功");
        return false;
    }
}
    