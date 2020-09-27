package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class examinees {
    /**
     * {
     * "name":注册姓名,
     * "phone":电话,
     * "pwd":密码,
     * "idCard":身份证,
     * "gender":性别,
     * "bkPhone":备用电话,
     * "photoPath":图片路径
     * }
     */
    private Integer id;
    private String name;
    private String phone;
    private String pwd;
    private String idCard;
    private Integer gender;
    private String bkPhone;
    private Integer status;
    private String createTime;
    private String modifyTime;
    private String photoPath;

    /**
     * 以下属性不是数据库属性
     */
    private Integer examId;
    private Integer examRoomId;
}
    