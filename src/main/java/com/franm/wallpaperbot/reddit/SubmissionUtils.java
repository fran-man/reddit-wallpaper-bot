package com.franm.wallpaperbot.reddit;

import com.franm.wallpaperbot.Requests.SearchResponse;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public final class SubmissionUtils {
    public SubmissionUtils() {

    }
    public static String generatePostTitle(SearchResponse searchResponse){
        return "Top " + searchResponse.getQueryString() + " wallpapers from " + listToCSV(searchResponse.getSubredditsSeached()) + " for " + LocalDate.now(ZoneId.of("Europe/London")).toString();
    }

    public static String generatePostContent(SearchResponse searchResponse){
        StringBuilder strb = new StringBuilder("");
        strb.append("Searched: ");
        for(String subreddit : searchResponse.getSubredditsSeached()){
            strb.append(subreddit + ", ");
        }
        strb.deleteCharAt(strb.length() - 1);
        strb.deleteCharAt(strb.length() - 1);
        strb.append("\n\n");
        strb.append(searchResponse.getFormattedResult());
        String result = strb.toString();
        return result;
    }

    private static String listToCSV(List<String> strings) {
        StringBuilder strb = new StringBuilder("");
        for (String string:strings){
            strb.append(string).append(",");
        }
        strb.deleteCharAt(strb.length() - 1);

        return strb.toString();
    }
}
