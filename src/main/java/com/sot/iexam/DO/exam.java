package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString

public class exam implements Serializable {
    /**
     * {
     * "reviewWorkload":每个阅卷老师的阅卷量,
     * "name":考试名,
     * "capacity":考试容量,
     * "titleId":对应职称id,
     * "titleName":对应职称名,
     * 1999-09-10 22:01:03必须前置0
     * "registrationStartTime":报名开始时间 yyyy-MM-dd HH:mm:ss,(1月为01  1日为01 8时为08)
     * "registrationFinishTime":报名结束时间 yyyy-MM-dd HH:mm:ss,
     * "examinationStartTime":考试开始时间 yyyy-MM-dd HH:mm:ss,
     * "examinationEndTime":考试结束时间 yyyy-MM-dd HH:mm:ss,
     * "reviewStartTime":阅卷开始时间 yyyy-MM-dd HH:mm:ss,
     * "reviewEndTime":阅卷结束时间 yyyy-MM-dd HH:mm:ss,
     * "publishManId":发起人（当前操作人）id,
     * "publishManName":发起人姓名,
     * "comment":暂时空,
     * }
     */
    private Integer id;
    private Integer reviewWorkload;
    private String name;
    private Integer capacity;
    private String status;
    private Integer num;
    private Integer titleId;
    private String titleName;
    private String examinationStartTime;
    private String examinationEndTime;
    private String registrationFinishTime;
    private String registrationStartTime;
    private String reviewStartTime;
    private String reviewEndTime;
    private Integer publishManId;
    private String publishManName;
    private String publishTime;
    private String comment;
    private String createTime;
    private String modifyTime;
    private Double examFee;
    private String place;
    private String precautions;
    private String ticketPublicizeTime;
    private String gradePublicizeTime;
    private String ticketGenerateTime;
    private Double maxGrade;
    private Double passGrade;

    private String titlePhoto;

}
    