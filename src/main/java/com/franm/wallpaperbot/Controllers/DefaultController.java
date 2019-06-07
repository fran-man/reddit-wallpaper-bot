package com.franm.wallpaperbot.Controllers;

import com.franm.wallpaperbot.Requests.SubSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController{
  @Autowired
  private SubSearch subSch;

  private static final String DEFAULT_ENDPOINT = "/";

  @RequestMapping(value = DEFAULT_ENDPOINT, method = RequestMethod.GET)
  public String defaultMapping(){
    return "Hello!";
  }

  @RequestMapping(value = "callreddit", method = RequestMethod.GET)
  public String callReddit(){
    this.subSch.searchSubWithString("wallpaper", "skyrim");
    return "Hello from reddit!";
  }
}
