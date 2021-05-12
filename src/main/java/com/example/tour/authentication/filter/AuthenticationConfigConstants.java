package com.example.tour.authentication.filter;

public class AuthenticationConfigConstants {
    public static final String SECRET = "tour_secret";
    public static final long EXPIRATION_TIME = 3600000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/new";
}