package com.sot.iexam.service.front;

import com.sot.iexam.DO.exam;
/**
 * @author Kimbobo
 */
public interface ExamTicketService {
    /**
     * 生成准考证号
     *
     * @param examInfo 传入考试信息
     * @return 能生成true 不能false
     */

    boolean generateTickets(exam examInfo);
}
    