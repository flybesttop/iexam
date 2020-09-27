package com.sot.iexam.service.front;

import org.quartz.SchedulerException;

/**
 * @Author: Kimbobo
 * @Date: 2019/12/10 14:36
 */
public interface SchedulerService {

    void scheduleExamStatusJob(String jobName, String group, String conditionTime, Integer examId, String statusToChange);

    void showList(String group);

    void cancelSchedule(String data, String group);


}
