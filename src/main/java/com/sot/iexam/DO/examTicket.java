package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class examTicket implements Serializable {
    private Integer id;
    private Integer examId;
    private Integer examineesId;
    private String ticketNumber;
    private Integer status;
    private String createTime;
    private String modifyTime;

    /**
     * 下面的不是数据库里的字段了
     */
    private Integer examRoomNumber;
    private Integer siteNumber;
}
    