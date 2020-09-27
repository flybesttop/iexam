package com.sot.iexam.service.front.impl;

import com.github.pagehelper.PageHelper;
import com.sot.iexam.DAO.mybatis.mapper.*;
import com.sot.iexam.DO.*;
import com.sot.iexam.exceptions.IExamException;
import com.sot.iexam.service.front.ExamTicketService;
import com.sot.iexam.util.Timmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kimbobo
 */
@Service
public class ExamTickerServiceImpl implements ExamTicketService {

    @Autowired
    classRoomMapper classRoomMapper;
    @Autowired
    examTicketMapper examTicketMapper;
    @Autowired
    registrationMapper registrationMapper;
    @Autowired
    examRoomMapper examRoomMapper;
    @Autowired
    examMapper examMapper;
    @Autowired
    gradeMapper gradeMapper;


    @Override
    public boolean generateTickets(exam examInfo) {

        if (!examInfo.getStatus().equals("111")) {
            StringBuilder builder = new StringBuilder();
            if (examInfo.getStatus().charAt(0) == '0') {
                builder.append("监考人还没分配。");
            }
            if (examInfo.getStatus().charAt(1) == '0') {
                builder.append("阅卷人还没分配。");
            }
            if (examInfo.getStatus().charAt(2) == '0') {
                builder.append("教室还没分配");
            }
            throw new IExamException(builder.toString());

        }
        Integer count = examTicketMapper.getTicketNumberByExamId(examInfo.getId());
        if (count > 0) {
            return true;
        }
        String time = Timmer.now();


        int registrationOffset = 0;  //报名人数分配的起始

        int num = examInfo.getNum();
        List<examRoom> usedClassInfo = examRoomMapper.getExamRoomByExamId(examInfo.getId());
        //教室分配
        for (examRoom examRoom : usedClassInfo) {
            if (num == 0) {
                break;
            }
            //座位号(3位)从1开始计数
            int siteNumber = 1;

            String[] timeInfo = examInfo.getExaminationEndTime().split(" ")[0].split("-");

            //考生和准考证分配
            PageHelper.offsetPage(registrationOffset, examRoom.getCapacity());
            List<registration> registrationInfo = registrationMapper.getRegistrationByExamId(examInfo.getId());
            num -= registrationInfo.size();
            System.out.println("考生信息:____");
            for (registration registration : registrationInfo) {
                //年(4)+月(2)+日(2)+职称号(4)+考场号(3)+座位号(3)
                //这里分配准考证
                String builder = timeInfo[0] +
                        (String.format("%s", timeInfo[1])) +
                        (String.format("%s", timeInfo[2])) +
                        (String.format("%04d", examInfo.getTitleId())) +
                        (String.format("%03d", examRoom.getNo())) +
                        (String.format("%03d", siteNumber));
                System.out.printf("      考生id：%d -----准考证信息：%s\n", registration.getExamineesId(), builder);

                examTicket ticket = new examTicket();
                ticket.setCreateTime(time);
                ticket.setModifyTime(time);
                ticket.setTicketNumber(builder);
                ticket.setExamId(registration.getExamId());
                ticket.setStatus(1);
                ticket.setExamineesId(registration.getExamineesId());
                examTicketMapper.insert(ticket);
                grade grade = new grade();
                grade.setCreateTime(time);
                grade.setExamRoomId(examRoom.getId());
                grade.setTicketNumber(builder);
                grade.setExamId(examInfo.getId());
                grade.setGrade(0.00);
                grade.setStatus(0);
                grade.setExamineeId(registration.getExamineesId());
                gradeMapper.insert(grade);
                siteNumber++;
            }
            registrationOffset += examRoom.getCapacity();

        }
        examInfo.setStatus("4");
        examMapper.update(examInfo);
        return true;

    }

}
    