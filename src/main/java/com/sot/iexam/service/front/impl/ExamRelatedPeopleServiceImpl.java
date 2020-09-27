package com.sot.iexam.service.front.impl;

import com.github.pagehelper.PageHelper;
import com.sot.iexam.DAO.mybatis.mapper.*;
import com.sot.iexam.DO.*;
import com.sot.iexam.VO.examArrangeVo;
import com.sot.iexam.VO.invigilatorListVo;
import com.sot.iexam.VO.reviewerExamListVo;
import com.sot.iexam.VO.roomVo;
import com.sot.iexam.exceptions.IExamException;
import com.sot.iexam.service.front.ExamRelatedPeopleService;
import com.sot.iexam.util.Timmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Kimbobo
 */
@Service
public class ExamRelatedPeopleServiceImpl implements ExamRelatedPeopleService {
    @Autowired
    invigilatorMapper invigilatorMapper;
    @Autowired
    reviewerMapper reviewerMapper;
    @Autowired
    invigilatorExamRoomMapper invigilatorExamRoomMapper;
    @Autowired
    examRoomMapper examRoomMapper;
    @Autowired
    reviewerExamMapper reviewerExamMapper;
    @Autowired
    examMapper examMapper;
    @Autowired
    reviewerExamRoomMapper reviewerExamRoomMapper;
    @Autowired
    gradeMapper gradeMapper;

    @Override
    public List<examArrangeVo> getExamArrangeInfo(Integer examId) {
        List<examArrangeVo> examArrangeVoList = new ArrayList<>();
        List<examRoom> examRoomList = examRoomMapper.getExamRoomByExamId(examId);

        for (examRoom e : examRoomList) {
            examArrangeVo ee = new examArrangeVo();
            ee.setExamRoom(e);
            XUser reviewer = reviewerExamRoomMapper.getXuerByExamRoomId(e.getId());
            List<XUser> invigilators = invigilatorExamRoomMapper.getInvigilatorsByExamRoomId(e.getId());
            ee.setReviewer(reviewer);
            ee.setInvigilatorList(invigilators);
            List<grade> grades = gradeMapper.getGradeListByExamRoomId(e.getId());
            ee.setGradeList(grades);
            examArrangeVoList.add(ee);
        }


        return examArrangeVoList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Queue> analyseReviewerAndInvigilators(exam examInfo) {
        //获取所有的监考人
        List<Integer> invigilatorsId = invigilatorMapper.getAllInvigilators();
        //获取所有的阅卷人
        List<Integer> reviewersId = reviewerMapper.getReviewersIdByTitleId(examInfo.getTitleId());


        //取交集
        List<Integer> pureInvigilatorsId = new ArrayList<>(invigilatorsId);
        pureInvigilatorsId.removeAll(reviewersId);
        //去交集
        List<Integer> MixedIds = new ArrayList<>(invigilatorsId);
        MixedIds.removeAll(pureInvigilatorsId);
        //去交集
        List<Integer> pureReviewersId = new ArrayList<>(reviewersId);
        pureReviewersId.removeAll(MixedIds);

        //在获取在这段时间内有时间安排的监考人


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTime = formatter.format(LocalDateTime.parse(examInfo.getExaminationStartTime(), formatter).plusMinutes(-30));
        String endTime = formatter.format(LocalDateTime.parse(examInfo.getExaminationEndTime(), formatter).plusMinutes(30));
        List<Integer> list1 = invigilatorExamRoomMapper.getUnAvailableInvigilators(startTime, endTime);
        //获取所有在这个时间内有安排的监考人
        //  System.out.println(list1);
        //去掉所有不能安排的监考人
        pureInvigilatorsId.removeAll(list1);
        MixedIds.removeAll(list1);

        //获取在这个时间段内有安排的阅卷人
        List<Integer> list2 = reviewerMapper.getUnAvailableReviewers(examInfo.getReviewStartTime(), examInfo.getReviewEndTime());
        // System.out.println(list2);
        //去掉所有不能安排的阅卷人
        pureReviewersId.removeAll(list2);
        MixedIds.removeAll(list2);
//
//        System.out.println("纯监考" + pureInvigilatorsId);
//        System.out.println("混合" + MixedIds);
//        System.out.println("纯阅卷" + pureReviewersId);

        Map<String, Queue> dataMap = new HashMap<>(8);
        dataMap.put("pureInvigilatorsId", new LinkedList(pureInvigilatorsId));
        dataMap.put("MixedIds", new LinkedList(MixedIds));
        dataMap.put("pureReviewersId", new LinkedList(pureReviewersId));
        return dataMap;
    }


    @Override
    @SuppressWarnings("unchecked")
    public boolean checkAndGenerateInvigilator(exam examInfo, Map<String, Queue> dataMap) {
        StringBuilder status = new StringBuilder(examInfo.getStatus());
        if (status.charAt(2) != '1') {
            throw new IExamException("无法在教室生成之前安排监考人");
        }

        HashMap<Integer, Integer> examRoomCount = new HashMap<>();
        List<invigilatorExamRoom> managedInfo = invigilatorExamRoomMapper.getInvigilatorByExamId(examInfo.getId());
        for (invigilatorExamRoom invigilatorExamRoom : managedInfo) {
            examRoomCount.merge(invigilatorExamRoom.getExamRoomId(), 1, Integer::sum);
        }
        for (Integer integer : examRoomCount.keySet()) {
            System.out.println(integer + "---->" + examRoomCount.get(integer));
        }

        //要求监考人在不参加阅卷也不参加其他监考
        Queue pureInvigilatorsId = dataMap.get("pureInvigilatorsId");
        Queue mixedIds = dataMap.get("MixedIds");
        //去重
        pureInvigilatorsId.removeAll(managedInfo);
        mixedIds.removeAll(managedInfo);
        //优先分配纯阅卷的id
        //其次分配混合的id
        List<examRoom> examRooms = examRoomMapper.getExamRoomInfoByExamId(examInfo.getId());
        int invigilatorsInNeed = 0;
        for (examRoom examRoom : examRooms) {
            invigilatorsInNeed += examRoom.getInvigilatorNum();
        }
        invigilatorsInNeed -= managedInfo.size();
        if (invigilatorsInNeed > pureInvigilatorsId.size() + mixedIds.size()) {
            throw new IExamException(String.format("监考人数不足,需要%d个，只有%d个", invigilatorsInNeed, mixedIds.size() + pureInvigilatorsId.size()));
        }

        for (examRoom examRoom : examRooms) {
            System.out.printf("%s教室需要监考%d个,分配如下\n", examRoom.getClassRoomId(), examRoom.getInvigilatorNum());
            int invigilatorNum = examRoom.getInvigilatorNum() - (examRoomCount.get(examRoom.getId()) == null ? 0 : examRoomCount.get(examRoom.getId()));

            Integer poll = null;
            invigilatorExamRoom invigilatorExamRoom = new invigilatorExamRoom();
            for (int j = 0; j < invigilatorNum; j++) {
                if (pureInvigilatorsId.size() > 0) {
                    poll = (Integer) pureInvigilatorsId.poll();
                } else {
                    poll = (Integer) mixedIds.poll();
                }
                invigilatorExamRoom.setEndTime(examRoom.getEndTime());
                invigilatorExamRoom.setExamRoomId(examRoom.getId());
                invigilatorExamRoom.setFromTime(examRoom.getFromTime());
                invigilatorExamRoom.setInvigilatorId(poll);
                invigilatorExamRoom.setExamId(examInfo.getId());

                invigilatorExamRoomMapper.insert(invigilatorExamRoom);
                System.out.println(invigilatorExamRoom);
            }

        }

        status.replace(0, 1, "1");
        examInfo.setStatus(status.toString());
        examMapper.updateStatus(examInfo);
        return true;
    }

    @Override
    public boolean checkAndGenerateReviewer(exam examInfo, Map<String, Queue> dataMap) {
        Queue pureReviewersId = dataMap.get("pureReviewersId");
        Queue mixedIds = dataMap.get("MixedIds");

        System.out.println(mixedIds);
        //managedReviewExamIds的size就是阅卷老师的数量
        List<reviewerExam> managedReviewExamIds = reviewerMapper.getManagedReviewersByExamId(examInfo.getId());

        Map<Integer, Integer> managedWorkLoad = new HashMap<>(32);
        Map<Integer, Integer> m = new HashMap<>(32);
        for (reviewerExam reviewerExam : managedReviewExamIds) {
            List<Integer> ids = reviewerExamRoomMapper.getManagedRoomByReviewerExamId(reviewerExam.getId());
            for (Integer integer : ids) {
                //获取安排过阅卷的阅卷人的工作量
                examRoom room = examRoomMapper.getExamRoomByIdSingle(integer);
                managedWorkLoad.merge(reviewerExam.getReviewerId(), room.getCapacity(), Integer::sum);

                m.put(integer, 1);
            }
        }

        int totalWork = examInfo.getNum();
        int workload = examInfo.getReviewWorkload();
        int reviewTeacherNumNeed = (int) Math.ceil(totalWork * 1.0 / workload);
        System.out.printf("一般来说需要%d个老师阅卷\n", reviewTeacherNumNeed);

        reviewTeacherNumNeed -= managedReviewExamIds.size();

        List<examRoom> examRoomArrangement = examRoomMapper.getExamRoomByExamId(examInfo.getId());
        List<examRoom> temp = new ArrayList<>();
        for (examRoom examRoom : examRoomArrangement) {
            if (m.get(examRoom.getId()) == null) {
                temp.add(examRoom);
            }
        }
        //获取没有安排的教室的id
        examRoomArrangement = temp;
        examRoomArrangement.sort(new Comparator<examRoom>() {
            @Override
            public int compare(examRoom o1, examRoom o2) {
                if (o1.getCapacity() > o2.getCapacity()) {
                    return -1;
                } else if (o1.getCapacity().equals(o2.getCapacity())) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });


        int finalEachTimes = Math.min(examRoomArrangement.size(), reviewTeacherNumNeed);
        if (reviewTeacherNumNeed <= 0) {
            finalEachTimes = examRoomArrangement.size();
        }
        Integer poll = null;

        //获取需要阅卷人的id，
        List<reviewerExam> idList = new ArrayList<>();
        for (int i = 0; i < managedReviewExamIds.size(); i++) {
            reviewerExam tmp = new reviewerExam();
            Integer reviewerId = managedReviewExamIds.get(i).getReviewerId();
            tmp.setTempWorkLoad(managedWorkLoad.get(reviewerId) == null ? 0 : managedWorkLoad.get(reviewerId));
            tmp.setReviewerId(reviewerId);
            idList.add(tmp);
            Integer managedWorkLoadOfReviewer = managedWorkLoad.get(reviewerId);
            if (managedWorkLoadOfReviewer != null) {
                //去掉已经安排的工作量
                totalWork -= managedWorkLoadOfReviewer;
            }

        }

        int loop = 0;
        while (totalWork > 0 && loop < finalEachTimes) {
            reviewerExam tmp = new reviewerExam();
            if (pureReviewersId.size() > 0) {
                poll = (Integer) pureReviewersId.poll();
                tmp.setTempWorkLoad(0);
                tmp.setReviewerId(poll);
                idList.add(tmp);
            } else {
                if (mixedIds.size() > 0) {
                    poll = (Integer) mixedIds.poll();
                    tmp.setTempWorkLoad(0);
                    tmp.setReviewerId(poll);
                    idList.add(tmp);
                }
            }
            totalWork -= workload;
            loop++;
        }

        if (idList.size() == 0) {
            throw new IExamException("阅卷人数不够，无法自动生成考试");
        }
        idList.sort(new Comparator<reviewerExam>() {
            @Override
            public int compare(reviewerExam o1, reviewerExam o2) {
                return o1.getTempWorkLoad().compareTo(o2.getTempWorkLoad());
            }
        });
        for (int i = 0; i < examRoomArrangement.size(); i++) {
            reviewerExam tmp = idList.get(0);
            tmp.setTempWorkLoad(tmp.getTempWorkLoad() + examRoomArrangement.get(i).getCapacity());
            idList.sort(new Comparator<reviewerExam>() {
                @Override
                public int compare(reviewerExam o1, reviewerExam o2) {
                    return o1.getTempWorkLoad().compareTo(o2.getTempWorkLoad());
                }
            });

            reviewerExam reviewerExam1 = reviewerExamMapper.getReviewerManage(tmp.getReviewerId(), examInfo.getId());
            if (reviewerExam1 == null) {
                reviewerExam1 = new reviewerExam();
                reviewerExam1.setExamId(examInfo.getId());
                reviewerExam1.setReviewerId(tmp.getReviewerId());
                reviewerExam1.setReviewStartTime(examInfo.getReviewStartTime());
                reviewerExam1.setReviewEndTime(examInfo.getReviewEndTime());
                reviewerExam1.setStatus(0);
                reviewerExamMapper.insert(reviewerExam1);
            }
            //添加阅卷和教师的一对多关系
            reviewerExamRoom reviewerExamRoom = new reviewerExamRoom();
            reviewerExamRoom.setExamRoomId(examRoomArrangement.get(i).getId());
            reviewerExamRoom.setReviewerExamId(reviewerExam1.getId());
            reviewerExamRoomMapper.insert(reviewerExamRoom);
        }
        for (reviewerExam integer : idList) {
            System.out.println(integer.getReviewerId() + "---------->" + integer.getTempWorkLoad());
        }
        StringBuilder builder = new StringBuilder(examInfo.getStatus());
        builder.replace(1, 2, "1");
        System.out.println("data1-->" + examInfo.getStatus());
        System.out.println("data-->" + builder.toString());
        examInfo.setStatus(builder.toString());
        examMapper.updateStatus(examInfo);
        return true;
    }

    @Override
    public Map getInvigilatorExamList(Integer page, Integer size, invigilatorExamRoom conditionsInObj) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);

        PageHelper.startPage(page, size);
        List<invigilatorExamRoom> invigilatorExamList = invigilatorExamRoomMapper.getInvigilatorExamList(conditionsInObj);
        List<invigilatorListVo> invigilatorListVos = new ArrayList<>();
        for (invigilatorExamRoom temp : invigilatorExamList) {
            invigilatorListVo ilo = new invigilatorListVo();
            ilo.setInvigilatorExamRoom(temp);
            ilo.setInvigilator(invigilatorMapper.getInvigilatorById(temp.getInvigilatorId()));
            ilo.setExam(examMapper.getExamById(temp.getExamId()));
            ilo.setRoomVo(examRoomMapper.getExamRoomById(temp.getExamRoomId()));
            invigilatorListVos.add(ilo);
        }
        m.put("data", invigilatorListVos);
        m.put("count", invigilatorExamRoomMapper.getInvigilatorExamListCount(conditionsInObj));
        return m;
    }

    @Override
    public Map getReviewerExamListVo(Integer page, Integer size, reviewerExam conditionsInObj) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);

        PageHelper.startPage(page, size);
        List<reviewerExam> reviewerExamList = reviewerExamMapper.getReviewerExamList(conditionsInObj);
        List<reviewerExamListVo> reviewerExamListVos = new ArrayList<>();
        for (reviewerExam reviewerExam : reviewerExamList) {
            reviewerExamListVo relo = new reviewerExamListVo();
            relo.setExam(examMapper.getExamById(reviewerExam.getExamId()));
            relo.setReviewer(reviewerMapper.getReviewerById(reviewerExam.getReviewerId()));
            relo.setExamRoomList(reviewerExamRoomMapper.getByReviewerExamId(reviewerExam.getId()));
            relo.setReviewerExam(reviewerExam);
            reviewerExamListVos.add(relo);
        }
        m.put("data", reviewerExamListVos);
        m.put("count", reviewerExamMapper.getReviewerExamListCount(conditionsInObj));
        return m;
    }

    @Override
    public Map getArrangedInvigilator(Integer page, Integer size, invigilatorExamRoom conditionsInObj) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);

        PageHelper.startPage(page, size);
        List<roomVo> reviewerExamList = invigilatorExamRoomMapper.getArrangedInvigilator(conditionsInObj);
        m.put("data", reviewerExamList);
        m.put("count", invigilatorExamRoomMapper.getReviewerExamArrangeCount(conditionsInObj));
        return m;
    }

    @Override
    public List<XUser> getPeopleInfo(List list) {
        return invigilatorMapper.getInfo(list);
    }

    @Override
    public Map getPeopleInfoPage(List list, Integer page, Integer size) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }

        Map<String, Object> m = new HashMap<>(8);

        PageHelper.startPage(page, size);

        List<XUser> xUserList = invigilatorMapper.getInfo(list);

        Integer count = invigilatorMapper.getInfoCount(list);

        m.put("data", xUserList);
        m.put("count", count);

        return m;
    }

    @Override
    public String resetInvigilator(Integer examId) {
        exam exam = examMapper.getExamById(examId);
        invigilatorMapper.resetInvigilator(examId);


        StringBuilder status = new StringBuilder(exam.getStatus());
        exam.setStatus(status.replace(0, 1, "0").toString());

        examMapper.update(exam);
        return status.toString();
    }

    @Override
    public Map addInvigilatorExamRoom(invigilatorExamRoom invigilatorExamRoom, Map map) {


        String time = Timmer.now();
        exam exam = examMapper.getExamById(invigilatorExamRoom.getExamId());
        StringBuilder builder = new StringBuilder(exam.getStatus());
        exam.setStatus(builder.replace(0, 1, "0").toString());

        examMapper.updateStatus(exam);
        invigilatorExamRoom.setModifyTime(time);
        invigilatorExamRoom.setCreateTime(time);
        invigilatorExamRoomMapper.insert(invigilatorExamRoom);
        XUser info = invigilatorExamRoomMapper.getInvigilatorInfoById(invigilatorExamRoom.getInvigilatorId());
        map.put("xUser", info);
        map.put("exam", exam);
        return map;
    }

    @Override
    public void cancelInvigilatorExam(invigilatorExamRoom invigilatorExamRoom, Map map) {
        exam exam = examMapper.getExamById(invigilatorExamRoom.getExamId());
        StringBuilder builder = new StringBuilder(exam.getStatus());
        exam.setStatus(builder.replace(0, 1, "0").toString());
        map.put("exam", exam);
        examMapper.updateStatus(exam);
        invigilatorExamRoomMapper.deleteByExamIdAndInvigilatorId(invigilatorExamRoom);
    }

    @Override
    public String resetReviewer(Integer examId) {
        exam exam = examMapper.getExamById(examId);
        reviewerExamRoomMapper.resetReviewer(examId);
        reviewerExamMapper.deleteByExamId(examId);

        StringBuilder status = new StringBuilder(exam.getStatus());
        exam.setStatus(status.replace(1, 2, "0").toString());
        examMapper.update(exam);
        return status.toString();
    }

    @Override
    public Map getReviewerExamList(Integer page, Integer size, reviewerExam conditionsInObj) {
        if (page == null) {
            page = 1;
        }
        if (size == null) {
            size = 10;
        }
        Map<String, Object> m = new HashMap<>(8);

        PageHelper.startPage(page, size);
        List<reviewerExam> reviewerExamList = reviewerExamMapper.getReviewerExamList(conditionsInObj);
        m.put("data", reviewerExamList);
        m.put("count", reviewerExamMapper.getReviewerExamListCount(conditionsInObj));
        return m;
    }

    @Override
    public void cancelReviewerExamRoom(Integer reviewerExamId, Integer examRoomId, Integer examId, Map map) {
        exam exam = examMapper.getExamById(examId);
        StringBuilder builder = new StringBuilder(exam.getStatus());
        exam.setStatus(builder.replace(1, 2, "0").toString());
        map.put("exam", exam);
        examMapper.updateStatus(exam);
        reviewerExamRoomMapper.cancelReviewerExamRoom(reviewerExamId, examRoomId);
    }

    @Override
    public void addReviewerExamRoom(reviewerExamRoom reviewerExamRoom) {
        reviewerExamRoomMapper.insert(reviewerExamRoom);
    }

    @Override
    public void cancelReviewerExam(Integer reviewerExamId, Integer examId, Map map) {
        exam exam = examMapper.getExamById(examId);
        StringBuilder builder = new StringBuilder(exam.getStatus());
        exam.setStatus(builder.replace(1, 2, "0").toString());
        map.put("exam", exam);
        examMapper.updateStatus(exam);
        reviewerExamRoomMapper.deleteByReviewerExamId(reviewerExamId);
        reviewerExamMapper.deleteById(reviewerExamId);
    }

    @Override
    public void addReviewerExam(reviewerExam reviewerExam, Map map) {
        exam exam = examMapper.getExamById(reviewerExam.getExamId());
        StringBuilder builder = new StringBuilder(exam.getStatus());
        exam.setStatus(builder.replace(1, 2, "0").toString());
        map.put("exam", exam);
        examMapper.updateStatus(exam);
        reviewerExam.setStatus(0);
        reviewerExamMapper.insert(reviewerExam);
    }
}
    