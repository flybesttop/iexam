package com.sot.iexam.VO;

import com.sot.iexam.DO.XUser;
import com.sot.iexam.DO.examNews;
import com.sot.iexam.DO.newsFiles;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class examNewsVo {
    private examNews examNews;
    private XUser xUser;
    private List<newsFiles> newsFilesList;
}
    