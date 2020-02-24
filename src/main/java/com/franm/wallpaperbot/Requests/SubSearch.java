package com.franm.wallpaperbot.Requests;

import com.franm.wallpaperbot.Format.PrettyUrlFormatter;
import com.franm.wallpaperbot.reddit.TokenManager;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SubSearch{
  private static final Logger LOGGER = LoggerFactory.getLogger(SubSearch.class);

  private static final String SEARCH_URL_PREFIX = "https://www.reddit.com/r/";
  private static final String SEARCH_URL_FUNCTION = "/search.json?q=";
  private static final String SEARCH_URL_SUFFIX_DAY = "&sort=top&restrict_sr=1&t=day";

  private HttpClient client = HttpClientBuilder.create().build();

  @Autowired
  private PrettyUrlFormatter formatter;

  @Autowired
  private TokenManager tknMgr;

  public SubSearch(){

  }

  public SearchResponse searchSubWithString(String sub, String searchTerm){
    tknMgr.getToken();
    SearchResponse schResp = new SearchResponse();
    HttpGet request = new HttpGet(SEARCH_URL_PREFIX + sub + SEARCH_URL_FUNCTION + searchTerm + SEARCH_URL_SUFFIX_DAY);

	   // add request header
	   request.addHeader("User-Agent", "USER_AGENT");
     try{
         HttpResponse response = client.execute(request);
        ListingParser parser = new ListingParser(IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name()));
        for(JsonNode node : parser.extractValuesFromResults("url")) {
        	log.debug(node.asText("Invalid Node..."));
        }
        schResp.setFormattedResult(formatter.formatWithDelimiter(parser.getListingChildren(), "\n\n"));
        schResp.setQueryString(searchTerm);
        schResp.getSubredditsSeached().add(sub);
        return schResp;
      }
      catch (Exception ex){
        log.error("Error Calling URL!", ex);
        return schResp;
      }
     finally {
        request.releaseConnection();
     }
  }
}
