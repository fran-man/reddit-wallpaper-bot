package com.franm.wallpaperbot.Format;

import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class PlainTextFormatter implements OutputFormatter<JsonNode> {
    @Override
    public String format(List<JsonNode> results) {
        return formatWithDelimiter(results, "\n");
    }

    public String formatWithDelimiter(List<JsonNode> results, String delimiter) {
        StringBuilder bldr = new StringBuilder();
        if(results.size() == 0){
            return "";
        }
        for(JsonNode node : results){
            bldr.append(node.asText() + delimiter);
        }
        bldr.setLength(bldr.length() - 1);

        return bldr.toString();
    }
}
