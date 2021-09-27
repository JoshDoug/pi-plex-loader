package com.jds.loaderapi.config;

import com.jds.loaderapi.security.JwtAuthenticationFilter;
import com.jds.loaderapi.security.LoginAuthenticationFilter;
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
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import java.security.Key;

import static com.jds.loaderapi.security.SecurityConstants.SECRET;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class APISecurity extends WebSecurityConfigurerAdapter {

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

    // WOOOOH IT FUCKING WORKS
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Note: antMatchers are considered in order, so a catch-all matcher should go last (e.g. "/**"),
        // otherwise it'll match every request and requests to "/h2/**" will require the user to be authenticated
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilter(new LoginAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), getJwtParser()));
    }


}
