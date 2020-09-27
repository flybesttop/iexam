package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class newsFiles {
    private Integer id;
    private String filePath;
    private Integer newsId;
    private String fileTitle;
    private String createTime;
    private String modifyTime;
}
