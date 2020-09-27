package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class examRoom implements Serializable {
    private Integer id;
    private Integer classRoomId;
    private Integer examId;
    private Integer invigilatorNum;
    private String fromTime;
    private String endTime;
    private String createTime;
    private String modifyTime;
    private Integer capacity;
    private Integer no;
    private String roomName;

}
    