package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class reviewerTitle implements Serializable {
    private Integer id;
    private Integer reviewerId;
    private Integer titleId;
    private Integer reviewTimes;
    private String addTime;
    private String modifyTime;
    private Integer status;

}
    