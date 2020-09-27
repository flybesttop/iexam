package com.sot.iexam.service.front.impl;

import com.github.pagehelper.PageHelper;
import com.sot.iexam.DAO.mybatis.mapper.ControversialGradeMapper;
import com.sot.iexam.DAO.mybatis.mapper.examMapper;
import com.sot.iexam.DAO.mybatis.mapper.examRoomMapper;
import com.sot.iexam.DAO.mybatis.mapper.gradeMapper;
import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.examRoom;
import com.sot.iexam.DO.grade;
import com.sot.iexam.VO.gradeVo;
import com.sot.iexam.VO.ticketVo;
import com.sot.iexam.service.front.GradeService;
import com.sot.iexam.util.Timmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    gradeMapper gradeMapper;
    @Autowired
    examMapper examMapper;
    @Autowired
    examRoomMapper examRoomMapper;
    @Autowired
    ControversialGradeMapper controversialGradeMapper;

    @Override
    public void addGrade(grade grade) {
        String time = Timmer.now();
        grade.setCreateTime(time);
        grade.setModifyTime(time);
        gradeMapper.insert(grade);
    }

    @Override
    public void updateGrade(grade grade) {
        String time = Timmer.now();
        grade.setModifyTime(time);
        gradeMapper.update(grade);
    }

    @Override
    public Map getGradeList(Integer page, Integer size, grade grade) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);
        PageHelper.startPage(page, size);
        List<grade> examineesList = gradeMapper.getGradeList(grade);
        m.put("data", examineesList);
        m.put("count", gradeMapper.getGradeListCount(grade));
        return m;

    }

    @Override
    public Map getMyGradeList(Integer page, Integer size, grade conditionsInObj) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);
        PageHelper.startPage(page, size);

        List<grade> examineesList = gradeMapper.getGradeList(conditionsInObj);
        List<gradeVo> gradeVoList=new ArrayList<>();
        int count=gradeMapper.getGradeListCount(conditionsInObj);

        for (grade gg:examineesList) {
            gradeVo ggg=new gradeVo();
            ggg.setGrade(gg);
            exam exam=examMapper.getExamById(gg.getExamId());
            if (!exam.getStatus().equals("10")){
                continue;
            }
            ggg.setExam(exam);
            ggg.setControversialGrade(controversialGradeMapper.getCG(gg.getId()));
            gradeVoList.add(ggg);
        }

        m.put("data", gradeVoList);
        m.put("count",count);
        return m;
    }

    @Override
    public Map getGradeListToSearch(Integer page, Integer size, grade conditionsInObj) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);
        PageHelper.startPage(page, size);
        List<grade> examineesList = gradeMapper.getGradeList(conditionsInObj);
        exam exam=examMapper.getExamById(conditionsInObj.getExamId());
        List<grade> examineesList2 = gradeMapper.getGradeList(conditionsInObj);
        int passNum=0;
        int noPassNum=0;
        for (grade ggg:examineesList2) {
            if (ggg.getGrade()<exam.getPassGrade()){
                noPassNum++;
            }else{
                passNum++;
            }
        }

        List<ticketVo> ticketVoList=new ArrayList<>();

        for (grade gg:examineesList) {
            examRoom examRoom=examRoomMapper.getExamRoomByIdSingle(gg.getExamRoomId());
            ticketVo ticketVo=new ticketVo();
            ticketVo.setExamRoom(examRoom);
            ticketVo.setTicket(gg);
            ticketVoList.add(ticketVo);
        }

        m.put("noPassNum",noPassNum);
        m.put("passNum",passNum);
        m.put("data", ticketVoList);
        m.put("count", gradeMapper.getGradeListCount(conditionsInObj));
        return m;
    }
}
    