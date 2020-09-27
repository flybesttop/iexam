package com.sot.iexam;

import com.sot.iexam.DAO.mybatis.mapper.*;
import com.sot.iexam.DO.*;
import com.sot.iexam.service.front.ExamService;
import com.sot.iexam.service.front.ExamTicketService;
import com.sot.iexam.util.Timmer;
import org.apache.catalina.LifecycleState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IexamApplicationTests {
    @Autowired
    examMapper examMapper;
    @Autowired
    registrationMapper registrationMapper;
    @Autowired
    examineesMapper examineesMapper;
    @Autowired
    ExamTicketService examTicketService;



//    @Test
//    public void contextLoads() {
//        exam exam=examMapper.getExamById(59);
//        examTicketService.generateTickets(exam);
//    }


    //生成学生
    @Test
    public void contextLoads() {
        exam exam=new exam();
        exam.setStatus("1");
        List<exam> examList=examMapper.getExamList(exam);
        List<examinees> examineesList=examineesMapper.getExamineesListAll();
        for (int i = 0; i < examList.size(); i++) {

//            HashSet set=getRandomlist();
//
//            ArrayList list=new ArrayList(set);

            for (int j = 0; j < 74; j++) {
                registration registration=new registration();
                registration.setExamId(examList.get(i).getId());
                registration.setExamineesId(examineesList.get(j).getId());
                registration.setModifyTime(Timmer.now());
                registration.setCreateTime(Timmer.now());
                registration.setStatus(1);
                registrationMapper.insert(registration);

            }
            examList.get(i).setNum(74);
            examMapper.update(examList.get(i));
        }
    }



}
