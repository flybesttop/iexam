package com.sot.iexam.service.front.impl;

import com.github.pagehelper.PageHelper;
import com.sot.iexam.DAO.mybatis.mapper.examineesMapper;
import com.sot.iexam.DO.examinees;
import com.sot.iexam.VO.examineesListVo;
import com.sot.iexam.service.front.ExamineesService;
import com.sot.iexam.util.Timmer;
import com.zlzkj.core.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kimbobo
 */
@Service
public class ExamineesServiceImpl implements ExamineesService {

    @Autowired
    examineesMapper examineesMapper;

    @Override
    public examinees login(String account, String password) {
        examinees user=examineesMapper.findByAccountAndPassword(account,password);
        if (user!=null){
            return user;
        }
        return null;
    }

    @Override
    public boolean updatePassword(Integer examineesId, String newPwd, String oldPwd) {
        examinees examinees = examineesMapper.getExamineesInfoById(examineesId);
        if (!examinees.getPwd().equals(oldPwd)){
            return false;
        }
        examinees.setPwd(newPwd);
        examineesMapper.update(examinees);
        return true;
    }

    @Override
    public void queryInsertUser() {
        examinees examinees = new examinees();
        for (int i = 0; i < 20; i++) {
            examinees.setGender(1);
            examinees.setPhone("178161803" + i);
            examinees.setName("cse" + i);
            examinees.setPwd("123456");
            examinees.setIdCard("IDCADR");
            examineesMapper.insertUser(examinees);
        }

    }

    @Override
    public boolean insert(examinees examinees) {
        if (examineesMapper.checkExistence(examinees) > 0) {
            return false;
        }
        examinees.setStatus(1);
        examinees.setCreateTime(Timmer.now());
        examinees.setModifyTime(Timmer.now());
        examineesMapper.insert(examinees);
        return true;
    }

    @Override
    public examinees getExamineesInfoById(Integer examineesId) {
        examinees info = examineesMapper.getExamineesInfoById(examineesId);
        if (info.getPhotoPath() != null)
            info.setPhotoPath(UploadUtils.parseFileUrl(info.getPhotoPath()));
        return info;
    }

    @Override
    public Map getExamineesList(Integer page, Integer size, examinees examinees) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);
        PageHelper.startPage(page, size);
        List<examineesListVo> examineesList = examineesMapper.getExamineesList(examinees);
        for (examineesListVo e : examineesList) {
            if (e.getExaminees().getPhotoPath() != null) {
                e.getExaminees().setPhotoPath(UploadUtils.parseFileUrl(e.getExaminees().getPhotoPath()));
            }
        }
        m.put("data", examineesList);
        m.put("count", examineesMapper.getCount(examinees));
        return m;
    }

    @Override
    public boolean updateExaminees(examinees examinees) {
        examinees.setModifyTime(Timmer.now());
        examineesMapper.update(examinees);
        return false;
    }
}
    