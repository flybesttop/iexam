package com.sot.iexam.VO;


import com.sot.iexam.DO.XUser;
import com.sot.iexam.DO.classRoom;
import com.sot.iexam.DO.examRoom;
import com.sot.iexam.DO.invigilator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Kimbobo
 */
@Setter
@Getter
@ToString
public class roomVo {
    private Integer examRoomId;
    private classRoom classRoom;
    private examRoom examRoom;
    private List<XUser> xUsers;

}
    