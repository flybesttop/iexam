package com.sot.iexam.VO;

import com.sot.iexam.DO.examTicket;
import com.sot.iexam.DO.examinees;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Kimbobo
 */
@Setter
@Getter
@ToString
public class examineesListVo {

    private examinees examinees;
    private examTicket examTicket;

}
    