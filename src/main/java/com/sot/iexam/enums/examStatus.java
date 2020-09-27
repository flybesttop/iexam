package com.sot.iexam.enums;

import lombok.Getter;
import lombok.Setter;

@Getter

public enum examStatus {
    WAIT_REGISTER("0", "考试报名未开始"),//考试报名还没开始
    ON_RESISTER("1", "正在报名"), //正在报名
    FINISH_REGISTER("2", "暂留"), //暂留  报名结束
    GENERATE_BEGIN("000", "考试安排中"),//正在自动生成
    /**
     * 考试自动生成状态位
     * 0  0  0
     * 监 阅 教
     * 考 卷 室
     * 位 位 位
     * --------------位
     */
    GENERATE_FINISH("111", "安排完毕"),
    GENERATED("4", "考试安排结束"),//此时准考证生成
    TICKET_PUBLISH("5", "准考证发布"),

    EXAM_START("6", "考试进行中"),//考试进行中
    EXAM_END("7", "考试结束"),//考试进行中
    REVIEWING("8", "阅卷中"),//考试结束
    REVIEW_END("9", "阅卷结束"),//考试完毕
    PUBLISH_GRADE("10", "整体结束,成绩公布"),
    FAILED("999", "生成失败");


    String code;
    String info;

    examStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }


}
