package com.franm.wallpaperbot.reddit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedditToken {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private String expiry;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }
}
