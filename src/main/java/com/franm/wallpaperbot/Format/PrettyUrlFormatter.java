package com.franm.wallpaperbot.Format;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrettyUrlFormatter implements OutputFormatter<JsonNode> {
    @Override
    public String format(List<JsonNode> childNodes) {
        return formatWithDelimiter(childNodes, "\n");
    }

    public String formatWithDelimiter(List<JsonNode> childNodes, String delimiter) {
        StringBuilder bldr = new StringBuilder();
        if(childNodes.size() == 0){
            return "";
        }
        for(JsonNode node : childNodes){
            bldr.append("[" + getValFromNode(node, "title").replace("[", "").replace("]", "") + "]")
            .append("(" + getValFromNode(node, "url") + ")")
            .append(delimiter);
        }
        bldr.setLength(bldr.length() - 1);

        return bldr.toString();
    }

    private String getValFromNode(JsonNode node, String key){
        return node.findValues("data").get(0).get(key).asText();
    }
}
