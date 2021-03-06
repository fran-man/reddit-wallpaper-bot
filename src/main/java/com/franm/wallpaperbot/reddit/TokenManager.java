package com.franm.wallpaperbot.reddit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Component
@Slf4j
@PropertySource("classpath:credentials.properties")
public class TokenManager {
    private String username;
    private String pass;
    private String appID;
    private String appSecret;
    private LocalDateTime lastRefresh = LocalDateTime.now().minusDays(1);
    private ObjectMapper objMapper = new ObjectMapper();

    private HttpClient client = HttpClientBuilder.create().build();

    private RedditToken token;

    private static final String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";

    @Autowired
    public TokenManager(@Value("${reddit.user}") final String username, @Value("${reddit.pass}") final String pass, @Value("${reddit.appid}") final String appID, @Value("${reddit.appsecret}") final String appSecret) {
        this.username = username;
        this.pass = pass;
        this.appID = appID;
        this.appSecret = appSecret;
        log.info("Loaded TokenManager for user {}", this.username);
    }

    /**
     * Method to fetch the token from reddit
     *
     * @return The token as a string
     */
    public RedditToken getToken(){
        if(this.isTokenStale()){
            try {
                refreshTokenFromReddit();
            } catch (Exception e) {
                log.error("Exception refreshing token, it may be stale.", e.getMessage());
            }
            this.lastRefresh = LocalDateTime.now();
        }
        return this.token;
    }

    private void refreshTokenFromReddit() throws URISyntaxException, IOException {
        URIBuilder bldr = null;
        try {
            bldr = new URIBuilder(ACCESS_TOKEN_URL);
            bldr.setParameter("grant_type", "password").setParameter("username", this.username).setParameter("password", this.pass);
            HttpPost postReq = new HttpPost(bldr.build());
            postReq.addHeader(HttpHeaders.USER_AGENT, "WallpaperBot/0.1 by fran_the_man");
            postReq.addHeader(HttpHeaders.AUTHORIZATION,  "Basic " + Base64.getEncoder().encodeToString((this.appID + ":" + appSecret).getBytes(StandardCharsets.UTF_8)));

            //log.info(IOUtils.toString(client.execute(postReq).getEntity().getContent(), StandardCharsets.UTF_8.name()));
            log.info("Refreshing - Last refresh was " + this.lastRefresh);

            String tokenString = IOUtils.toString(client.execute(postReq).getEntity().getContent(), StandardCharsets.UTF_8.name());
            this.token = this.objMapper.readValue(tokenString, RedditToken.class);
        }
        catch(URISyntaxException ex){
            log.error("Incorrect URI" + ex.getMessage());
            throw ex;
        } catch (ClientProtocolException e) {
            log.error("Protocol Exception Refreshing Token: " + e.getMessage());
            throw e;
        } catch (IOException e) {
            log.error("IOException Refreshing Token: " + e.getMessage());
            throw e;
        }
    }

    private boolean isTokenStale(){
        return this.lastRefresh.isBefore(LocalDateTime.now().minusHours(1));
    }
}
