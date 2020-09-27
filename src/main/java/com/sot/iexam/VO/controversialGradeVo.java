package com.sot.iexam.VO;

import com.sot.iexam.DO.controversialGrade;
import com.sot.iexam.DO.exam;
import com.sot.iexam.DO.examinees;
import com.sot.iexam.DO.grade;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Kimbobo
 */
@Setter
@Getter
@ToString
public class controversialGradeVo {
    /**
     * 考生信息
     */

    private examinees examinees;
    /**
     * 成绩信息
     */
    private grade grade;
    /**
     * 异常申报信息
     */
    private controversialGrade controversialGrade;
    //考试信息
    private exam exam;
}
    