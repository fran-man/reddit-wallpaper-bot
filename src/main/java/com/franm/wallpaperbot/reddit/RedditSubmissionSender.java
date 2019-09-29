package com.franm.wallpaperbot.reddit;

import com.franm.wallpaperbot.Format.OutputFormatter;
import com.franm.wallpaperbot.Format.PlainTextFormatter;
import com.franm.wallpaperbot.Requests.SearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

@Component
@Slf4j
public class RedditSubmissionSender {
    private OutputFormatter formatter;
    private TokenManager tknMgr;
    private HttpClient client = HttpClientBuilder.create().build();

    private static final String SUBMIT_POST_URL = "https://oauth.reddit.com/api/submit";

    public RedditSubmissionSender(PlainTextFormatter formatter, TokenManager tknMger){
        this.formatter = formatter;
        this.tknMgr = tknMger;
    }

    public void submitPost(SearchResponse searchResponse){
        String accessToken = this.tknMgr.getToken().getAccessToken();
        log.debug("Access Token: {}", accessToken);
        try {
            URIBuilder uriBuilder = new URIBuilder(SUBMIT_POST_URL);
            HttpPost postreq = new HttpPost(uriBuilder.build());
            postreq.addHeader(HttpHeaders.USER_AGENT, "WallpaperBot/0.1 by fran_the_man");
            postreq.addHeader(HttpHeaders.AUTHORIZATION, "bearer " + accessToken);

            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("sr", "WallpaperBot"));
            postParameters.add(new BasicNameValuePair("text", this.generatePostContent(searchResponse)));
            postParameters.add(new BasicNameValuePair("title", this.generatePostTitle(searchResponse)));
            postParameters.add(new BasicNameValuePair("kind", "self"));

            postreq.setEntity(new UrlEncodedFormEntity(postParameters, StandardCharsets.UTF_8.name()));

            log.info(postreq.toString());
            log.info(IOUtils.toString(client.execute(postreq).getEntity().getContent(), StandardCharsets.UTF_8.name()));
        } catch (URISyntaxException e) {
            log.error("Error in URI: {}", e.getMessage());
        } catch (ClientProtocolException e) {
            log.error("Error submitting post to reddit: {}", e.getMessage());
        } catch (IOException e) {
            log.error("Error submitting post to reddit: {}", e.getMessage());
        }
    }

    private String generatePostTitle(SearchResponse searchResponse){
        return "Top " + searchResponse.getQueryString() + " wallpapers for " + LocalDate.now(ZoneId.of("Europe/London")).toString();
    }

    private String generatePostContent(SearchResponse searchResponse){
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
        log.debug(result);
        return result;
    }
}
