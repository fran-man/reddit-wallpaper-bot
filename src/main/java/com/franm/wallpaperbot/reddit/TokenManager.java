package com.franm.wallpaperbot.reddit;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

@Component
@Slf4j
public class TokenManager {
    private String username;
    private String pass;
    private String appID;
    private String appSecret;

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
        }
        catch(URISyntaxException ex){
            log.error("Incorrect URI" + ex.getMessage());
            return "";
        }
        return this.token;
    }
}
