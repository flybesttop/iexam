package com.sot.iexam.VO;

import com.sot.iexam.DO.XUser;
import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.examRoom;
import com.sot.iexam.DO.reviewerExam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Kimbobo
 */
@Setter
@Getter
@ToString
public class reviewerExamListVo {
    private exam exam;
    private XUser reviewer;
    private reviewerExam reviewerExam;
    private List<examRoom> examRoomList;
}
    