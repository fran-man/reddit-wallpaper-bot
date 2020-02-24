package com.franm.wallpaperbot.reddit;

import com.franm.wallpaperbot.Format.OutputFormatter;
import com.franm.wallpaperbot.Format.PlainTextFormatter;
import com.franm.wallpaperbot.Requests.SearchResponse;
import com.sun.net.httpserver.HttpsParameters;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
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
    private HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(
            RequestConfig.custom().setConnectTimeout(5000).build())
            .setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(5000).build()).build();

    private static final String SUBMIT_POST_URL = "https://oauth.reddit.com/api/submit";

    public RedditSubmissionSender(PlainTextFormatter formatter, TokenManager tknMger){
        this.formatter = formatter;
        this.tknMgr = tknMger;
    }

    public void submitPost(SearchResponse searchResponse){
        String accessToken = this.tknMgr.getToken().getAccessToken();
        log.debug("Access Token: {}", accessToken);
        URIBuilder uriBuilder = null;
        HttpPost postreq = new HttpPost();
        try {
            uriBuilder = new URIBuilder(SUBMIT_POST_URL);
            postreq = new HttpPost(uriBuilder.build());
            postreq.addHeader(HttpHeaders.USER_AGENT, "WallpaperBot/0.1 by fran_the_man");
            postreq.addHeader(HttpHeaders.AUTHORIZATION, "bearer " + accessToken);

            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("sr", "WallpaperBot"));
            postParameters.add(new BasicNameValuePair("text", SubmissionUtils.generatePostContent(searchResponse)));
            postParameters.add(new BasicNameValuePair("title", SubmissionUtils.generatePostTitle(searchResponse)));
            postParameters.add(new BasicNameValuePair("kind", "self"));

            postreq.setEntity(new UrlEncodedFormEntity(postParameters, StandardCharsets.UTF_8.name()));

            log.info(postreq.toString());
            log.debug(IOUtils.toString(client.execute(postreq).getEntity().getContent(), StandardCharsets.UTF_8.name()));
        } catch (URISyntaxException e) {
            log.error("Error in URI: {}", e.getMessage());
        } catch (ClientProtocolException e) {
            log.error("Error submitting post to reddit: {}", e.getMessage());
        } catch (IOException e) {
            log.error("Error submitting post to reddit: {}", e.getMessage());
        }
        finally {
            postreq.releaseConnection();
        }
    }
}
