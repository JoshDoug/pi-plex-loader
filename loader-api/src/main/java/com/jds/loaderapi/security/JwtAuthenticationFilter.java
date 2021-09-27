package com.jds.loaderapi.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.jds.loaderapi.security.SecurityConstants.*;

// TODO - figure out this filter ordering shit
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JwtParser jwtParser;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtParser jwtParser) {
        // Okay so maybe the reason we extend BasicAuthenticationFilter is because it takes authenticationManager in it's constructor?
        super(authenticationManager);

        // But BasicAuthentication has nothing to do with JwtAuthentication, so I don't really want to use it...
        // Overview of BasicAuthentication: https://en.wikipedia.org/wiki/Basic_access_authentication
        // It does use the Authorization header, and has a similar Basic <base64 encoded credentials> setup
        // (whereas JWT convention uses Bearer instead of Basic

//        Instead we can probably just wire it ourselves, is there any harm?
//        this.authenticationManager = authenticationManager;
        this.jwtParser = jwtParser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO
        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // TODO
        String token = request.getHeader(HEADER_STRING);

        if(token != null) {
            // TODO - why not have an autowired Jwts parser built with our secret key, is there a good reason not to do this?
            //  because it keeps the key in memory? Isn't it already in memory statically? And aren't we in trouble anyway?
            // Also, the parseClaimsJws also does the job of verifying the signature of the token and will throw an exception if it fails
            String user = jwtParser
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            // TODO - get everything else we need from the token
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        }

        return null;
    }
}
