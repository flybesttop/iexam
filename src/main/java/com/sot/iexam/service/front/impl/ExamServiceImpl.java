package com.sot.iexam.service.front.impl;

import com.github.pagehelper.PageHelper;
import com.sot.iexam.DAO.mybatis.mapper.*;
import com.sot.iexam.DO.*;
import com.sot.iexam.VO.ticketVo;
import com.sot.iexam.enums.examStatus;
import com.sot.iexam.exceptions.IExamException;
import com.sot.iexam.service.front.ExamService;
import com.sot.iexam.service.front.SchedulerService;
import com.sot.iexam.util.Timmer;
import com.zlzkj.core.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.Registration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Kimbobo
 */
@Service
public class ExamServiceImpl implements ExamService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    examRoomMapper examRoomMapper;
    @Autowired
    classRoomMapper classRoomMapper;
    @Autowired
    examMapper examMapper;
    @Autowired
    registrationMapper registrationMapper;
    @Autowired
    reviewerMapper reviewerMapper;
    @Autowired
    reviewerExamMapper reviewerExamMapper;
    @Autowired
    invigilatorExamRoomMapper invigilatorExamRoomMapper;
    @Autowired
    invigilatorMapper invigilatorMapper;
    @Autowired
    examTicketMapper examTicketMapper;
    @Autowired
    titleMapper titleMapper;
    @Autowired
    examineesMapper examineesMapper;
    @Autowired
    gradeMapper gradeMapper;
    @Autowired
    SchedulerService schedulerService;

    @Override
    public exam getExamInfo(Integer examId) {
        return examMapper.getExamById(examId);
    }

    @Override
    public boolean deleteExam(Integer examId) {
        return false;
    }

    @Override
    public Map getAllStdTicket(Integer examRoomId) {
        Map m = new HashMap();

        examRoom examRoom = examRoomMapper.getExamRoomByIdSingle(examRoomId);
        exam exam = examMapper.getExamById(examRoom.getExamId());
        List<ticketVo> ticketVoList = new ArrayList<>();

        List<grade> gradeList = gradeMapper.getGradeListByExamRoomId(examRoomId);

        for (grade gg : gradeList) {
            ticketVo ticketVo = new ticketVo();
            ticketVo.setTicket(gg);
            examinees examinees = examineesMapper.getExamineesInfoById(gg.getExamineeId());
            examinees.setPhotoPath(UploadUtils.parseFileUrl(examinees.getPhotoPath()));
            ticketVo.setExaminees(examinees);
            ticketVoList.add(ticketVo);
        }

        m.put("examRoom", examRoom);
        m.put("exam", exam);
        m.put("ticketVoList", ticketVoList);

        return m;
    }

    @Override
    public ticketVo getTicket(Integer userId, Integer examId) {

        exam exam = examMapper.getExamById(examId);
        examinees examinees = examineesMapper.getExamineesInfoById(userId);
        examinees.setPhotoPath(UploadUtils.parseFileUrl(examinees.getPhotoPath()));
        grade grade = gradeMapper.getTicket(userId, examId);
        examRoom examRoom = examRoomMapper.getExamRoomByIdSingle(grade.getExamRoomId());


        ticketVo ticketVo = new ticketVo();
        ticketVo.setExam(exam);
        ticketVo.setExaminees(examinees);
        ticketVo.setTicket(grade);
        ticketVo.setExamRoom(examRoom);

        return ticketVo;
    }

    @Override
    public Map getExamInfoListToReviewer(Integer page, Integer size, String search, Integer userId) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        if (search == null) {
            search = "";
        }
        Map<String, Object> m = new HashMap<>(8);
        PageHelper.startPage(page, size);
        m.put("data", examMapper.getExamInfoListToReviewer(search, userId));
        m.put("count", examMapper.getCountExamInfoListToReviewer(search, userId));
        return m;
    }

    @Override
    public Map getExamInfoListToPublish(Integer page, Integer size, String search) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        if (search == null) {
            search = "";
        }
        Map<String, Object> m = new HashMap<>(8);
        PageHelper.startPage(page, size);
        m.put("data", examMapper.getExamListToPublish(search));
        m.put("count", examMapper.getCountToPublish(search));
        return m;
    }


    @Override
    public Map getExamInfoListMyExams(Integer page, Integer size, Integer userId) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }

        Map<String, Object> m = new HashMap<>(8);

        PageHelper.startPage(page, size);
        List<exam> exams = registrationMapper.getRegistrationByExamineesIdMyExams(userId);

        for (exam item : exams) {
            item.setTitlePhoto(UploadUtils.parseFileUrl(titleMapper.getTitleByTitleId(item.getTitleId()).getPhotoPath()));
        }

        m.put("data", exams);
        m.put("count", registrationMapper.getCountRegistrationByExamineesIdMyExams(userId));
        return m;
    }

    @Override
    public Map getExamInfoListWithTitle(Integer page, Integer size, exam exam) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);
        PageHelper.startPage(page, size);

        List<exam> exams = examMapper.getExamList(exam);

        for (exam item : exams) {
            title tit = titleMapper.getTitleByTitleId(item.getTitleId());
            item.setTitlePhoto(UploadUtils.parseFileUrl(tit.getPhotoPath()));
        }

        m.put("data", exams);
        m.put("count", examMapper.getCount(exam));
        return m;
    }

    @Override
    public boolean addExam(exam exam) {
        exam.setCreateTime(Timmer.now());
        exam.setModifyTime(Timmer.now());
        exam.setStatus("0");
        exam.setNum(0);
        exam.setPublishTime(Timmer.now());
        timeSchedule(exam);
        examMapper.insert(exam);

        System.out.println(exam.getId());
        return true;
    }

    @Override
    public Map getExamInfoList(Integer page, Integer size, exam exam) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);
        PageHelper.startPage(page, size);
        m.put("data", examMapper.getExamList(exam));
        m.put("count", examMapper.getCount(exam));
        return m;
    }

    @Override
    public boolean updateExam(exam exam) {
        exam.setModifyTime(Timmer.now());
        examMapper.update(exam);
        return true;
    }

    @Override
    public boolean updateExamAndScheduler(exam exam) {
        exam.setModifyTime(Timmer.now());

        timeSchedule(exam);

        examMapper.update(exam);
        return true;
    }

    @Override
    public boolean updateStatus(exam exam) {
        exam.setModifyTime(Timmer.now());
        examMapper.updateStatus(exam);
        return true;
    }

    @Override
    public boolean checkStatus(exam exam, String status) {
        if ("canGenerate".equals(status)) {
            if (exam.getStatus().length() != 3) {
                throw new IExamException("考试当前不可安排");
            }
        }
        return true;
    }


    @Override
    public Map getExamVoList(Integer page, Integer size, examTicket examTicket) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);
        PageHelper.startPage(page, size);
        m.put("data", examMapper.getExamVoList(examTicket));
        m.put("count", examMapper.getExamVoCount(examTicket));
        return m;
    }

    void timeSchedule(exam exam) {
        String registrationStartTime = exam.getRegistrationStartTime();
        schedulerService.scheduleExamStatusJob("exam-" + exam.getId() + "registrationStartTime", "examStatus", registrationStartTime, exam.getId(), examStatus.ON_RESISTER.getCode());

        String registrationFinishTime = exam.getRegistrationFinishTime();
        schedulerService.scheduleExamStatusJob("exam-" + exam.getId() + "registrationFinishTime", "examStatus", registrationFinishTime, exam.getId(), examStatus.GENERATE_BEGIN.getCode());

        String ticketGenerateTime = exam.getTicketGenerateTime();
        schedulerService.scheduleExamStatusJob("exam-" + exam.getId() + "ticketGenerateTime", "examStatus", ticketGenerateTime, exam.getId(), examStatus.GENERATED.getCode());

        String ticketPublicizeTime = exam.getTicketPublicizeTime();
        schedulerService.scheduleExamStatusJob("exam-" + exam.getId() + "ticketPublicizeTime", "examStatus", ticketPublicizeTime, exam.getId(), examStatus.TICKET_PUBLISH.getCode());


        String examinationStartTime = exam.getExaminationStartTime();
        schedulerService.scheduleExamStatusJob("exam-" + exam.getId() + "examinationStartTime", "examStatus", examinationStartTime, exam.getId(), examStatus.EXAM_START.getCode());
        String examinationEndTime = exam.getExaminationEndTime();
        schedulerService.scheduleExamStatusJob("exam-" + exam.getId() + "examinationEndTime", "examStatus", examinationEndTime, exam.getId(), examStatus.EXAM_END.getCode());

        String reviewStartTime = exam.getReviewStartTime();
        schedulerService.scheduleExamStatusJob("exam-" + exam.getId() + "reviewStartTime", "examStatus", reviewStartTime, exam.getId(), examStatus.REVIEWING.getCode());

        String reviewEndTime = exam.getReviewEndTime();
        schedulerService.scheduleExamStatusJob("exam-" + exam.getId() + "reviewEndTime", "examStatus", reviewEndTime, exam.getId(), examStatus.REVIEW_END.getCode());

        String gradePublicizeTime = exam.getGradePublicizeTime();
        schedulerService.scheduleExamStatusJob("exam-" + exam.getId() + "gradePublicizeTime", "examStatus", gradePublicizeTime, exam.getId(), examStatus.PUBLISH_GRADE.getCode());

    }
}
    