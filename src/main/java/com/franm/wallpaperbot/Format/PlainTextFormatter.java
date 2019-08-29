package com.franm.wallpaperbot.Format;

import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;

public class PlainTextFormatter implements OutputFormatter<JsonNode> {
    @Override
    public String format(List<JsonNode> results) {
        StringBuilder bldr = new StringBuilder();
        if(results.size() == 0){
            bldr.append("No Results found!");
            return bldr.toString();
        }
        for(JsonNode node : results){
            bldr.append(node.asText() + "\n");
        }
        bldr.setLength(bldr.length() - 1);

        return bldr.toString();
    }
}