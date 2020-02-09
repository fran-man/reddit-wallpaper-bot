package com.franm.wallpaperbot.reddit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ListingParser {
    private String listingString;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ListingParser(String listingString) {
        this.listingString = listingString;
    }

    public List<JsonNode> extractValuesFromResults(String key) {

        List<JsonNode> children = this.getListingChildren();
        if (children.size() == 0) {
            log.info("No elements matching '" + key + "' in child nodes...");
            return new ArrayList<>();
        } else {
            List<JsonNode> resultNodes = children.stream().map(c -> c.findValues("data").get(0).get(key)).filter(c -> c != null).collect(Collectors.toList());
            return resultNodes.size() == 0 ? new ArrayList<>() : resultNodes;
        }
    }

    public List<JsonNode> getListingChildren() {
        JsonNode jsonNode = null;
        try {
            jsonNode = this.objectMapper.readTree(this.listingString);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        if (jsonNode == null) {
            return new ArrayList<>();
        } else {
            JsonNode childArray = jsonNode.findValues("data").get(0).findValues("children").get(0);
            ArrayList<JsonNode> result = new ArrayList<>();
            for(JsonNode child : childArray){
                result.add(child);
            }
            return result;
        }
    }
}
