package com.sot.iexam.VO;
import com.sot.iexam.DO.*;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class examArrangeVo {
    List<grade> gradeList;
    examRoom examRoom;
    List<XUser> invigilatorList;
    XUser reviewer;
}
