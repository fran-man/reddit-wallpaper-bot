package com.franm.wallpaperbot.reddit;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
@PropertySource("classpath:credentials.properties")
public class TokenManager {
    private String username;
    private String pass;
    private String appID;
    private String appSecret;

    private HttpClient client = HttpClientBuilder.create().build();

    private String token;

    private static final String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";

    @Autowired
    public TokenManager(@Value("${reddit.user}") final String username, @Value("${reddit.pass}") final String pass, @Value("${reddit.appid}") final String appID, @Value("${reddit.appsecret}") final String appSecret) {
        this.username = username;
        this.pass = pass;
        this.appID = appID;
        this.appSecret = appSecret;
        log.info("Loaded TokenManager for user {}",this.username);
    }

    /**
     * Method to fetch the token from reddit
     * TODO: Cache the token and only go to reddit if it is expired.
     * @return The token as a string
     */
    public String getToken(){
        URIBuilder bldr = null;
        try {
            bldr = new URIBuilder(ACCESS_TOKEN_URL);
            bldr.setParameter("grant_type", "password").setParameter("username", this.username).setParameter("password", this.pass);
            HttpPost postReq = new HttpPost(bldr.build());
            postReq.addHeader(HttpHeaders.USER_AGENT, "WallpaperBot/0.1 by fran_the_man");
            postReq.addHeader(HttpHeaders.AUTHORIZATION,  "Basic " + Base64.getEncoder().encodeToString((this.appID + ":" + appSecret).getBytes(StandardCharsets.UTF_8)));

            log.info(IOUtils.toString(client.execute(postReq).getEntity().getContent(), StandardCharsets.UTF_8.name()));
        }
        catch(URISyntaxException ex){
            log.error("Incorrect URI" + ex.getMessage());
            return "";
        }
        catch(IOException ioex){
            log.error("Error fetching token, returning potentially stale token... " + ioex.getMessage());
            return this.token;
        }
        return this.token;
    }
}
