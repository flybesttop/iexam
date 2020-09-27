package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class examNews {
    /**
     * {
     * "title":新闻标题,
     * "text":新闻具体的html,
     * "publishId":发布人（当前操作人的id）,
     * "examId":对应的考试id,
     * "type":新闻的具体类型
     * }
     */
    private Integer id;
    private String publishTime;
    private Integer status;
    private String title;
    private String createTime;
    private String modifyTime;
    private String text;
    private Integer publishId;
    private Integer examId;
    private Integer type;

    private String publishName;
    private List<newsFiles> newsFilesList;
}
    