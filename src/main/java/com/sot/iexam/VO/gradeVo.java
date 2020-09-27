package com.sot.iexam.VO;
import com.sot.iexam.DO.controversialGrade;
import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.grade;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class gradeVo {
    private exam exam;
    private grade grade;
    private controversialGrade controversialGrade;
}
