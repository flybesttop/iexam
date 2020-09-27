package com.sot.iexam.service.front;

import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.examRoom;

import java.util.List;
import java.util.Map;

/**
 * @author Kimbobo
 */
public interface RoomService {
    /**
     * 检查和自动分配考试教室
     *
     * @param examInfo 传入考试信息实体 {@link exam}
     * @return 能够分配返回true 否则返回false
     */
    boolean checkAndGenerateExamRoom(exam examInfo);

    Map getAllClassRoomCanUse(String startTime, String endTime,Integer page,Integer size,String room_search);

    List<examRoom> getExamRoomInfoByExamId(Integer examId);

    void addArrangement(examRoom examRoom);

    void deleteByExamRoomId(Integer examRoomId, Integer examId);

    String resetExamRoom(Integer examId);

    boolean updateExamRoom(examRoom examRoom);
}