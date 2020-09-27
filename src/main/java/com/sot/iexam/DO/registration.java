package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class registration implements Serializable {
    private Integer id;
    private Integer examId;
    private Integer examineesId;
    private Integer status;
    private String createTime;
    private String modifyTime;
    private List<examinees> examineesList;
}
    