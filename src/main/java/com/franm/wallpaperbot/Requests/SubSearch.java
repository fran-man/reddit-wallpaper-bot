package com.franm.wallpaperbot.Requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.HttpClient;
import org.springframework.stereotype.Component;

@Component
public class SubSearch{
  private static final Logger LOGGER = LoggerFactory.getLogger(SubSearch.class);

  private static final String SEARCH_URL_PREFIX = "https://www.reddit.com/r/";
  private static final String SEARCH_URL_FUNCTION = "/search.json?q=";
  private static final String SEARCH_URL_SUFFIX_WEEK = "&sort=top&restrict_sr=1&t=week";

  private HttpClient client = HttpClientBuilder.create().build();

  public SubSearch(){

  }

  public String searchSubWithString(String sub, String searchTerm){
    HttpGet request = new HttpGet(SEARCH_URL_PREFIX + sub + SEARCH_URL_FUNCTION + searchTerm + SEARCH_URL_SUFFIX_WEEK);

	   // add request header
	   request.addHeader("User-Agent", "USER_AGENT");
     try{
	      HttpResponse response = client.execute(request);
        LOGGER.info(response.getEntity().toString());
        return response.toString();
      }
      catch (Exception ex){
        LOGGER.error("Error Calling URL!", ex);
        return "";
      }
  }
}