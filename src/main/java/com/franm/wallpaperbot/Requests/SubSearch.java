package com.franm.wallpaperbot.Requests;

import com.franm.wallpaperbot.Format.PlainTextFormatter;
import com.franm.wallpaperbot.reddit.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.franm.wallpaperbot.reddit.ListingParser;

import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

@Component
public class SubSearch{
  private static final Logger LOGGER = LoggerFactory.getLogger(SubSearch.class);

  private static final String SEARCH_URL_PREFIX = "https://www.reddit.com/r/";
  private static final String SEARCH_URL_FUNCTION = "/search.json?q=";
  private static final String SEARCH_URL_SUFFIX_WEEK = "&sort=top&restrict_sr=1&t=week";

  private HttpClient client = HttpClientBuilder.create().build();

  private PlainTextFormatter formatter = new PlainTextFormatter();

  @Autowired
  private TokenManager tknMgr;

  public SubSearch(){

  }

  public String searchSubWithString(String sub, String searchTerm){
      tknMgr.getToken();
    HttpGet request = new HttpGet(SEARCH_URL_PREFIX + sub + SEARCH_URL_FUNCTION + searchTerm + SEARCH_URL_SUFFIX_WEEK);

	   // add request header
	   request.addHeader("User-Agent", "USER_AGENT");
     try{
	      HttpResponse response = client.execute(request);
        ListingParser parser = new ListingParser(IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name()));
        for(JsonNode node : parser.extractValuesFromResults("url")) {
        	LOGGER.info(node.asText("InvalidNode..."));
        }
        return formatter.format(parser.extractValuesFromResults("url"));
      }
      catch (Exception ex){
        LOGGER.error("Error Calling URL!", ex);
        return "";
      }
  }
}
