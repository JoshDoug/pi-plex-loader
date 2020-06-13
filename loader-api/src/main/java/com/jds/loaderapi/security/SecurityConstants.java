package com.jds.loaderapi.security;

import io.jsonwebtoken.SignatureAlgorithm;

// It's just a collection of security related constants, seems pretty sensible
public class SecurityConstants {
    // This could/should be longer for an actual application, generated with cli command `openssl rand -base64 12`
    public static final SignatureAlgorithm SECRET = SignatureAlgorithm.HS256;
//    public static final String SECRET = "wg1gcLkw4WYv7kyG";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days - TODO - what is a good time here? (Context dependent but I'd like to have some general thoughts)
    public static final String TOKEN_PREFIX = "Bearer "; // This is just the OAuth standard, could be something else but standard is better
    public static final String HEADER_STRING = "Authorization"; // Ewww it's got a `z` instead of `s`, sadly I don't make the conventions

    // Unsure of why this shouldn't just be in the users controller like every other endpoint mapping
//    public static final String SIGN_UP_URL = "/users/sign-up";
}
