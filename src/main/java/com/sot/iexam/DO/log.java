package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class log {
    private Integer id;
    private Integer operateType;
    private Integer operateUser;
    private String data;
    private String function;
    private String time;
    private String packageName;
    private String comment;


    /**
     * 以下非数据库字段
     */
    private String fromTime;
    private String endTime;
    private String operateUserName;
}
    