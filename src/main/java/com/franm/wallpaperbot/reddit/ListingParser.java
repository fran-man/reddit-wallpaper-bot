package com.franm.wallpaperbot.reddit;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

@Slf4j
public class ListingParser{
  private String listingString;
  private final ObjectMapper objectMapper = new ObjectMapper();
  public ListingParser(String listingString){
    this.listingString = listingString;
  }

  public List<JsonNode> extractValuesFromResults(String key){
    JsonNode jsonNode = null;
    try{
      log.info(this.listingString);
      jsonNode = this.objectMapper.readTree(this.listingString);
    }
    catch(IOException ex){
      log.error(ex.getMessage());
    }

    if(jsonNode == null){
      return new ArrayList<>();
    }
    else{
      return jsonNode.findValues(key);
    }
  }
  
	private List<JsonNode> getListingChildren() {
		JsonNode jsonNode = null;
		try {
			log.info(this.listingString);
			jsonNode = this.objectMapper.readTree(this.listingString);
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}

		if (jsonNode == null) {
			return new ArrayList<>();
		} else {
			return jsonNode.findValues("data").get(0).findValues("children").toArray();
		}
	}
}
