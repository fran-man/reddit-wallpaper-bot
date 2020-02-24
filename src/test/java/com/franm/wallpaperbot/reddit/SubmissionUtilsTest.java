package com.franm.wallpaperbot.reddit;

import com.franm.wallpaperbot.Requests.SearchResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class SubmissionUtilsTest {
    SearchResponse response = new SearchResponse();

    @Before
    public void init(){
        response.setFormattedResult("BLAH");
        response.setQueryString("QUERY");
        response.setSubredditsSeached(new ArrayList<String>());
        response.getSubredditsSeached().add("SUBREDDIT");
    }

    @Test
    public void testGenerateTitleAppendsSubName() {
        String generatedTitle = SubmissionUtils.generatePostTitle(response);
        assertTrue(generatedTitle.contains("wallpapers from SUBREDDIT for"));
    }
}
