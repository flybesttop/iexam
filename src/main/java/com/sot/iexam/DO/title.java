package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class title {
    private Integer id;
    private String titleName;
    private Integer status;
    private String createTime;
    private String modifyTime;
    private String photoPath;
    private String content;
}
    