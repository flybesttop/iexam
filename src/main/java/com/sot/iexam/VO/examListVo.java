package com.sot.iexam.VO;

import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.examRoom;
import com.sot.iexam.DO.examTicket;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Kimbobo
 */
@Setter
@Getter
@ToString
public class examListVo {
    private exam exam;
    private examTicket examTicket;
    private examRoom examRoom;
}
    