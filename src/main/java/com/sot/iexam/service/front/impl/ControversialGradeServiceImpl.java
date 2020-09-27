package com.sot.iexam.service.front.impl;

import com.github.pagehelper.PageHelper;
import com.sot.iexam.DAO.mybatis.mapper.ControversialGradeMapper;
import com.sot.iexam.DAO.mybatis.mapper.examMapper;
import com.sot.iexam.DO.controversialGrade;
import com.sot.iexam.VO.controversialGradeVo;
import com.sot.iexam.service.front.ControversialGradeService;
import com.sot.iexam.util.Timmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

@Service
public class ControversialGradeServiceImpl implements ControversialGradeService {

    @Autowired
    ControversialGradeMapper controversialGradeMapper;
    @Autowired
    examMapper examMapper;

    @Override
    public void addControversialGrade(controversialGrade controversialGrade) {
        String time = Timmer.now();
        controversialGrade.setCreateTime(time);
        controversialGrade.setModifyTime(time);
        controversialGrade.setStatus(1);

        controversialGradeMapper.insert(controversialGrade);
    }

    @Override
    public Map getControversialGradeList(Integer page, Integer size, controversialGrade conditionsInObj) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);
        PageHelper.startPage(page, size);
        List<controversialGradeVo> list = controversialGradeMapper.getControversialGradeListVo(conditionsInObj);
        for (controversialGradeVo c:list) {
            c.setExam(examMapper.getExamById(c.getGrade().getExamId()));
        }
        m.put("data", list);
        m.put("count", controversialGradeMapper.getControversialGradeListVoCount(conditionsInObj));
        return m;
    }

    @Override
    public void updateControversialGrade(controversialGrade controversialGrade) {
        controversialGradeMapper.update(controversialGrade);
    }
}
    