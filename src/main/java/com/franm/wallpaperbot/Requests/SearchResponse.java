package com.franm.wallpaperbot.Requests;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResponse {
    private String formattedResult;
    private List<String> subredditsSeached = new ArrayList<String>();
    private String queryString;
}
