package com.jds.loaderapi.config;

import com.jds.loaderapi.security.JwtAuthenticationFilter;
import com.jds.loaderapi.security.LoginAuthenticationFilter;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.security.Key;

import static com.jds.loaderapi.security.SecurityConstants.SECRET;

//@EnableWebSecurity
public class MultiSecurityConfig {

//
//    @Configuration
//    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        private Key getKey() {
            return Keys.secretKeyFor(SECRET);
        }

//        @Bean
        public JwtParser getJwtParser() {
            return Jwts.parserBuilder().setSigningKey(getKey()).build();
        }

        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/api/**", "/login")
                    .authenticated()
                    .and()
                    .addFilter(new LoginAuthenticationFilter(authenticationManager()))
                    .addFilter(new JwtAuthenticationFilter(authenticationManager(), getJwtParser()))
                    // this disables session creation on Spring Security
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

//    @Configuration
//    public static class H2SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//        protected void configure(HttpSecurity http) throws Exception {
//            http.authorizeRequests().antMatchers("/h2/**").permitAll();
//            http.csrf().disable();
//            http.headers().frameOptions().disable();
//        }
//    }
}
