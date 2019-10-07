package com.franm.wallpaperbot.Quartz;

import com.franm.wallpaperbot.Quartz.Jobs.RunSearchJob;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

@Configuration
public class JobConfig {
    @Bean
    public JobDetail ultraHDJob(@Qualifier("uhdJobDataMap") JobDataMap datamap){
        return JobBuilder.newJob(RunSearchJob.class).usingJobData(datamap).withIdentity(datamap.getString("name")).storeDurably(true).build();
    }

    @Bean
    public JobDataMap uhdJobDataMap(){
        JobDataMap uhdJobDataMap = new JobDataMap();
        uhdJobDataMap.put("name", "uhdJob");
        uhdJobDataMap.put("subreddit", "wallpaper");
        uhdJobDataMap.put("term", "3840");
        return uhdJobDataMap;
    }

    @Bean
    public CronTriggerFactoryBean cronTriggerFactory(@Qualifier("ultraHDJob") JobDetail jobDetail){
        CronTriggerFactoryBean cronTriggerFactory = new CronTriggerFactoryBean();
        cronTriggerFactory.setBeanName(jobDetail.getClass().toString());
        cronTriggerFactory.setDescription("Search Job for UHD Wallpapers");
        cronTriggerFactory.setJobDataMap(jobDetail.getJobDataMap());
        cronTriggerFactory.setCronExpression("0 * * ? * *");
        cronTriggerFactory.setJobDetail(jobDetail);
        return cronTriggerFactory;
    }
}
