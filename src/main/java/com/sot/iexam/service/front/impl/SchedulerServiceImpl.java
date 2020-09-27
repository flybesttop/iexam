package com.sot.iexam.service.front.impl;

import com.sot.iexam.scheduleJob.examStatusChangeJob;
import com.sot.iexam.service.front.SchedulerService;
import com.sot.iexam.util.Timmer;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

/**
 * @author Kimbobo
 */
@Service
public class SchedulerServiceImpl implements SchedulerService {
    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void scheduleExamStatusJob(String jobName, String group, String conditionTime, Integer examId, String statusToChange) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.deleteJob(JobKey.jobKey(jobName, group));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        JobDetail jobDetail = JobBuilder.newJob(examStatusChangeJob.class)
                .withIdentity(jobName, group).build();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("status", statusToChange);
        jobDataMap.put("examId", examId);
        LocalDateTime dateTime = LocalDateTime.parse(conditionTime, formatter);
        System.out.println(conditionTime + "---" + statusToChange);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(String.format("%d %d %d %d %d ? %d"
                        , dateTime.getSecond(), dateTime.getMinute(), dateTime.getHour(),
                        dateTime.getDayOfMonth(), dateTime.getMonth().getValue(), dateTime.getYear()));
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withSchedule(scheduleBuilder).build();
        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showList(String group) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            Set<JobKey> examStatus = scheduler.getJobKeys(GroupMatcher.groupEquals(group));
            for (JobKey status : examStatus) {
                JobDetail jobDetail = scheduler.getJobDetail(status);
                System.out.println(jobDetail.getJobDataMap().get("data"));
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelSchedule(String data, String group) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.deleteJob(JobKey.jobKey(data, group));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
    