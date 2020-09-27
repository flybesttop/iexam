package com.sot.iexam.VO;

import com.sot.iexam.DO.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Kimbobo
 */
@Setter
@Getter
@ToString
public class invigilatorListVo {
    private exam exam;
    private XUser invigilator;
    private roomVo roomVo;
    private invigilatorExamRoom invigilatorExamRoom;
}