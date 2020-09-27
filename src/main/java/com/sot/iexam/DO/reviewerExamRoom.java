package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class reviewerExamRoom {
    private Integer id;
    private Integer reviewerExamId;
    private Integer examRoomId;
    private String createTime;
    private String modifyTime;
}
    