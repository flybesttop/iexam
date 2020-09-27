package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Kimbobo
 */
@Setter
@Getter
@ToString
public class classRoom implements Serializable {
    private Integer id;
    private String name;
    private Integer capacity;
    private Integer status;
    private Integer teacherNum;
    private String createTime;
    private String modifyTime;

}
    