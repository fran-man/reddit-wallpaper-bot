package com.franm.wallpaperbot.reddit;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.JsonNode;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
public class ListingParser{
  private String listingString;
  private final ObjectMapper objectMapper = new ObjectMapper();
  public ListingParser(String listingString){
    this.listingString = listingString;
  }

  public List<JsonNode> extractValuesFromResults(String key){

    List<JsonNode> children = this.getListingChildren();
    if(children.size() == 0){
      return new ArrayList<>();
    }
    else{
      List<JsonNode> resultNodes = children.stream().flatMap(c -> c.findValues("data").get(0).findValues(key).stream()).filter(c -> c != null).collect(Collectors.toList());
      return resultNodes.size() == 0 ? new ArrayList<>() : resultNodes;
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
			return jsonNode.findValues("data").get(0).findValues("children");
		}
	}
}
