package com.sot.iexam.service.front;


import com.sot.iexam.DO.examinees;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Kimbobo
 */
public interface ExamineesService {

    examinees login(String username,String password);

    boolean updatePassword(Integer examineesId,String newPwd,String oldPwd);

    public void queryInsertUser();

    /**
     * 添加考生信息（主要是考生注册） 格式：{@link examinees}
     *
     * @param examinees 传入的考生JSON串
     * @return 添加成功true 失败false
     */
    boolean insert(examinees examinees);

    /**
     * 根据id查找报名考生信息
     *
     * @param examineesId 需要查找的报名考生的id
     */
    examinees getExamineesInfoById(Integer examineesId);

    /**
     * 获取考试注册考生信息
     * 页数从1开始，不传值默认第一页 大小10
     *
     * @param page 从多少开始
     * @param size 页大小
     * @return
     */
    Map getExamineesList(Integer page, Integer size, examinees examinees);

    boolean updateExaminees(examinees examinees);
}
