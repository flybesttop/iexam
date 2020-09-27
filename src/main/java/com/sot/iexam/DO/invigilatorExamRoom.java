package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class invigilatorExamRoom implements Serializable {
    private Integer id;
    private Integer invigilatorId;
    private Integer examRoomId;
    private Integer examId;
    private String fromTime;
    private String endTime;
    private String createTime;
    private String modifyTime;
}
    