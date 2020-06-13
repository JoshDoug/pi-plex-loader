package com.jds.loaderapi.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.security.Key;

import static com.jds.loaderapi.security.SecurityConstants.SECRET;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    // AuthenticationEntryPoint - normally redirects to a login form, but how does that work with a SPA?
    //  Do we just return an unauthorised/forbidden (unauthorised probably makes more sense?) and let the SPA handle the redirect?

    private Key getKey() {
        return Keys.secretKeyFor(SECRET);
    }

    @Bean
    public JwtParser getJwtParser() {
        return Jwts.parserBuilder().setSigningKey(getKey()).build();
    }

    @Bean
    public JwtBuilder getJwtBuilder() {
        return Jwts.builder().signWith(getKey());
    }

    // I don't even slightly understand this damn thing
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Note: antMatchers are considered in order, so a catch-all matcher should go last (e.g. "/**"),
        // otherwise it'll match every request and requests to "/h2/**" will require the user to be authenticated
        http.authorizeRequests()
                .antMatchers("/h2/**", "/login").permitAll()
                .anyRequest().authenticated()
                .and().csrf().ignoringAntMatchers("/h2/**")
                .and().headers().frameOptions().sameOrigin();

        /*
        http.authorizeRequests()
                .anyRequest().permitAll()
                .and().csrf().ignoringAntMatchers("/h2/**") // Make H2-Console non-secured; for debug purposes
                .and().headers().frameOptions().sameOrigin(); // Allow pages to be loaded in frames from the same origin; needed for H2-Console
         */
    }
}
