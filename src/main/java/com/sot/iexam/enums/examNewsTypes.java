package com.sot.iexam.enums;

public enum examNewsTypes {
    NORMAL(0),//最普通的类型，没有什么考试报名连接
    EXAM_REGISTRATION_INFO(1),//有报名连接的类型
    EXAM_RESULT(2),//考试结果查询类型
    OTHERS(3),//代扩展
    ;

    Integer code;

    examNewsTypes(int i) {
        code = i;
    }
}
