package com.franm.wallpaperbot.reddit

import com.fasterxml.jackson.annotation.JsonProperty

data class RedditTokenKotlin(@JsonProperty("access_token") var accessToken: String, @JsonProperty(
        "expires_in") var expiry: String, @JsonProperty("scope") var scope: String, @JsonProperty(
        "token_type") var tokenType: String)