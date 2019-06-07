package com.franm.wallpaperbot.Controllers;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController{

  private static final String DEFAULT_ENDPOINT = "/";

  @RequestMapping(value = DEFAULT_ENDPOINT, method = RequestMethod.GET)
  public String defaultMapping(){
    return "Hello!";
  }
}
