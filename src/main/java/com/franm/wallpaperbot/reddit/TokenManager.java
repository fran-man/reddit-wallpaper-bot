package com.franm.wallpaperbot.reddit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenManager {
    private String username;
    private String pass;
    private String appID;
    private String appSecret;

    private String token;

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

    }
}
