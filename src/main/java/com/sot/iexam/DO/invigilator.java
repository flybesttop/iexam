package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class invigilator implements Serializable {
    private Integer id;
    private Integer invigilatorId;
    private Integer status;
    private String createTime;
    private String modifyTime;

}
    