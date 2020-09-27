package com.sot.iexam.service.front;

import com.sot.iexam.DO.log;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kimbobo
 * @Date: 2019/12/10 8:26
 */
public interface LogService {
    void insert(log log);
    Map getLoglist(Integer page, Integer size, Integer type);
}
