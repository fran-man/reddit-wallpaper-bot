package com.franm.wallpaperbot.Format;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface OutputFormatter<T extends JsonNode> {
    String format(List<T> results);
}
