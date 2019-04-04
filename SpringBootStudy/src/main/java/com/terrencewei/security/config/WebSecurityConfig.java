package com.terrencewei.security.config;

import com.terrencewei.security.service.MyUserDetailsService;
import com.terrencewei.security.utils.MyPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new MyUserDetailsService();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(getUserDetailsService()).passwordEncoder(new MyPasswordEncoder());
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // disable csrf
                .csrf().disable()
                // auth all request
                .authorizeRequests().anyRequest().authenticated()
                // auth form login
                .and().formLogin().loginPage("/loginPage")
                // login success url
                .defaultSuccessUrl("/").failureUrl("/loginPage").permitAll()
                // allow access to logout
                .and().logout().permitAll()
                // auth access to api calls
                .and().authorizeRequests().antMatchers("/api/**").authenticated()
                // auth type: basic
                .and().httpBasic();
    }

}