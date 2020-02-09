package com.franm.wallpaperbot.Quartz.Jobs;

import com.franm.wallpaperbot.Requests.SearchResponse;
import com.franm.wallpaperbot.Requests.SubSearch;
import com.franm.wallpaperbot.reddit.RedditSubmissionSender;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RunSearchJob extends QuartzJobBean {
    @Autowired
    private SubSearch subSch;

    @Autowired
    private RedditSubmissionSender sender;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        String name = dataMap.getString("name");
        String subreddit = dataMap.getString("subreddit");
        String term = dataMap.getString("term");
        log.info("Running: {} with subreddit = {}", name, subreddit);
        SearchResponse resp = this.subSch.searchSubWithString(subreddit, term);
        if (resp.getFormattedResult().equals("")) { log.info("Empty result"); } else {this.sender.submitPost(resp); };
    }
}