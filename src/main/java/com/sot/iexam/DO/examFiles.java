package com.sot.iexam.DO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Kimbobo
 */
@Setter
@Getter
@ToString
public class examFiles {
    private Integer id;

    private String filePath;
    private Integer examId;
    private String fileTitle;
    private String createTime;
    private String modifyTime;



    private String fileName;
}
    