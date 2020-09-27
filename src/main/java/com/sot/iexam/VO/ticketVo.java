package com.sot.iexam.VO;

import com.sot.iexam.DO.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ticketVo {
    examinees examinees;
    grade ticket;
    exam exam;
    examRoom examRoom;
}
