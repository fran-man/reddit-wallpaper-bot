package com.franm.wallpaperbot.reddit;

import com.franm.wallpaperbot.Format.OutputFormatter;
import com.franm.wallpaperbot.Format.PlainTextFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedditSubmissionSender {
    private OutputFormatter formatter;
    private TokenManager tknMgr;
    public RedditSubmissionSender(PlainTextFormatter formatter, TokenManager tknMger){
        this.formatter = formatter;
        this.tknMgr = tknMger;
    }

    public void submitPost(String postContent, String subreddit, String title){
        this.tknMgr.getToken();
    }
}
