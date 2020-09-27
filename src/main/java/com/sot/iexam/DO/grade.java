package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class grade {
    private Integer id;
    private Integer examineeId;
    private Integer examId;
    private String ticketNumber;
    private Double grade;
    private Integer status;
    private Integer operatorId;
    private String createTime;
    private String modifyTime;
    private Integer examRoomId;
}
    