package com.holyrandom.library.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }
}
