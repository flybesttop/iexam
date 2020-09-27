package com.sot.iexam.service.front.impl;

import com.github.pagehelper.PageHelper;
import com.sot.iexam.DAO.mybatis.mapper.*;
import com.sot.iexam.DO.classRoom;
import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.examRoom;
import com.sot.iexam.enums.examStatus;
import com.sot.iexam.exceptions.IExamException;
import com.sot.iexam.service.front.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kimbobo
 */
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    examRoomMapper examRoomMapper;
    @Autowired
    classRoomMapper classRoomMapper;
    @Autowired
    examMapper examMapper;
    @Autowired
    invigilatorExamRoomMapper invigilatorExamRoomMapper;
    @Autowired
    reviewerExamMapper reviewerExamMapper;
    @Autowired
    reviewerExamRoomMapper reviewerExamRoomMapper;

    @Override
    public boolean checkAndGenerateExamRoom(exam examInfo) {
        StringBuilder builder = new StringBuilder(examInfo.getStatus());
        if (builder.charAt(2) == '1') {
            return false;
        }
        //考试开始时间的前半个小时和后半个小时要求没有安排
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(examInfo.getExaminationStartTime(), formatter).plusMinutes(-30);
        String startTime = formatter.format(localDateTime);
        String endTime = formatter.format(LocalDateTime.parse(examInfo.getExaminationEndTime(), formatter).plusMinutes(30));


        //当前考试使用到的教室的信息
        boolean assignment = false;
        //获取已经已经安排的教室信息
        List<classRoom> usedClassInfo = examRoomMapper.getExamRoomArrangement(examInfo.getId());
        int no = usedClassInfo.size();
        int num = examInfo.getNum();
        for (classRoom classRoom : usedClassInfo) {
            num -= classRoom.getCapacity();
        }
        usedClassInfo = new ArrayList<>();
        //自动生成教室
        List<classRoom> freeClassRooms = classRoomMapper.getAllClassRoomCanUse(startTime, endTime);
        //判断教室容量是否足够进行分配
        try {
            checkExamCapacity(num, freeClassRooms);
        } catch (Exception e) {
            throw new IExamException(e.getMessage());
        }
        //如果教室足够分配，那么在原有的基础上继续分配
        for (classRoom room : freeClassRooms) {
            if (num > 0) {
                assignment = true;
                usedClassInfo.add(room);
                num -= room.getCapacity();
            } else {
                break;
            }
        }

        //考场号(3位)从1开始计数

        System.out.println("教室分配如下");
        num = examInfo.getNum();
        for (classRoom classRoom : usedClassInfo) {
            no++;
//            System.out.println(classRoom.getName());
            if (assignment) {
                examRoom examRoom = new examRoom();
                examRoom.setClassRoomId(classRoom.getId());
                examRoom.setEndTime(examInfo.getExaminationEndTime());
                examRoom.setFromTime(examInfo.getExaminationStartTime());
                examRoom.setExamId(examInfo.getId());
                examRoom.setInvigilatorNum(2);
                examRoom.setNo(no);
                examRoom.setRoomName(classRoom.getName());
                examRoom.setCapacity(classRoom.getCapacity());
                examRoomMapper.insert(examRoom);
            }

            num -= classRoom.getCapacity();
        }

        builder.replace(2, 3, "1");
        examInfo.setStatus(builder.toString());
        examMapper.updateStatus(examInfo);

        return true;
    }

    @Override
    public Map getAllClassRoomCanUse(String startTime, String endTime, Integer page, Integer size, String room_search) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(startTime, formatter).plusMinutes(-30);
        startTime = formatter.format(localDateTime);
        endTime = formatter.format(LocalDateTime.parse(endTime, formatter).plusMinutes(30));


        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }

        Map<String, Object> m = new HashMap<>(8);

        PageHelper.startPage(page, size);

        List<classRoom> classRoomList = classRoomMapper.getAllClassFreeRoomCanUse(startTime, endTime, room_search);
        Integer count = classRoomMapper.getAllClassFreeRoomCanUseNum(startTime, endTime, room_search);

        m.put("data", classRoomList);
        m.put("count", count);

        return m;
    }

    @Override
    public List<examRoom> getExamRoomInfoByExamId(Integer examId) {
        return examRoomMapper.getExamRoomInfoByExamId(examId);
    }

    @Override
    public void addArrangement(examRoom examRoom) {
        exam exam = new exam();
        exam.setId(examRoom.getExamId());
        exam.setStatus(examStatus.GENERATE_BEGIN.getCode());
        examMapper.updateStatus(exam);
        examRoomMapper.insert(examRoom);
    }

    @Override
    public void deleteByExamRoomId(Integer examRoomId, Integer examId) {
        exam exam = new exam();
        exam.setId(examId);
        exam.setStatus(examStatus.GENERATE_BEGIN.getCode());
        examMapper.updateStatus(exam);
        examRoomMapper.deleteByExamRoomId(examRoomId);
    }

    @Override
    public String resetExamRoom(Integer examId) {
        exam exam = new exam();
        exam.setStatus(examStatus.GENERATE_BEGIN.getCode());
        exam.setId(examId);
        examRoomMapper.deleteByExamId(examId);
        invigilatorExamRoomMapper.deleteByExamId(examId);
        reviewerExamRoomMapper.resetReviewer(examId);
        reviewerExamMapper.deleteByExamId(examId);

        examMapper.updateStatus(exam);
        return exam.getStatus();
    }

    @Override
    public boolean updateExamRoom(examRoom examRoom) {
        invigilatorExamRoomMapper.deleteByExamRoomId(examRoom.getId());
        examRoomMapper.update(examRoom);
        return true;
    }

    /**
     * 检查考试容量是否符合要求
     *
     * @param num           考试需要的座位数
     * @param usedClassInfo 考场列表
     */
    private void checkExamCapacity(int num, List<classRoom> usedClassInfo) {
        if (num <= 0) {
            return;
        }
        int ClassRoomCanUseCapacity = 0;
        for (classRoom classRoom : usedClassInfo) {
            ClassRoomCanUseCapacity += classRoom.getCapacity();
        }
        if (num > ClassRoomCanUseCapacity) {
            throw new IExamException(String.format("教室容量不够，需要%d,实际%d", num, ClassRoomCanUseCapacity));
        }

    }
}
