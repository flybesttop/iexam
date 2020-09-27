package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class reviewerExam implements Serializable {

    private Integer id;
    private Integer examId;
    private Integer reviewerId;
    private String reviewStartTime;
    private String reviewEndTime;
    private Integer status;
    private String createTime;
    private String modifyTime;


    /**
     * 以下不是数据库字段
     */
    private Integer tempWorkLoad;

}
    