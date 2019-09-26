package com.franm.wallpaperbot.Controllers;

import com.franm.wallpaperbot.Requests.SubSearch;
import com.franm.wallpaperbot.reddit.RedditSubmissionSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class DefaultController{
  @Autowired
  private SubSearch subSch;

  @Autowired
  private RedditSubmissionSender submissionSender;

  private static final String DEFAULT_ENDPOINT = "/";

  @RequestMapping(value = DEFAULT_ENDPOINT, method = RequestMethod.GET)
  public String defaultMapping(){
    return "Hello!";
  }

  @RequestMapping(value = "callreddit", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
  public @ResponseBody
  String callReddit(@RequestParam(defaultValue = "wallpaper") String subreddit, @RequestParam(defaultValue = "3840") String term){
    this.submissionSender.submitPost("","","");
    return this.subSch.searchSubWithString(subreddit, term);
  }
}
