package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Kimbobo
 */
@Setter
@Getter
@ToString
public class controversialGrade {
    private Integer id;
    private Integer examineeId;
    private Integer gradeId;
    private Integer status;
    private String respond;
    private Integer type;
    private String createTime;
    private String modifyTime;
}
    