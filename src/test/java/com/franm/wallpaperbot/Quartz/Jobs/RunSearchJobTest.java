package com.franm.wallpaperbot.Quartz.Jobs;

import com.franm.wallpaperbot.Requests.SearchResponse;
import com.franm.wallpaperbot.Requests.SubSearch;
import com.franm.wallpaperbot.reddit.RedditSubmissionSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class RunSearchJobTest {

    @Mock
    public SubSearch subSch;

    @Mock
    public RedditSubmissionSender sender;

    @InjectMocks
    private RunSearchJob searchJob = new RunSearchJob();

    @Mock
    public JobExecutionContext jobExCon;

    @Mock
    public JobDataMap dataMap;

    @Before
    public void init(){
        SearchResponse schResp = new SearchResponse();
        schResp.setFormattedResult("");
        when(subSch.searchSubWithString(any(),any())).thenReturn(schResp);
        when(jobExCon.getMergedJobDataMap()).thenReturn(dataMap);
        when(dataMap.getString(any())).thenReturn("");
    }

    @Test
    public void PostIsNotSubmittedOnEmptyResult(){
        try {
            searchJob.executeInternal(jobExCon);
            verify(sender, times(0)).submitPost(any());
        } catch (JobExecutionException e) {
            e.printStackTrace();
        }
    }
}
