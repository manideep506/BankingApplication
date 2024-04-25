package com.example.BankingApplication.config;

import com.example.BankingApplication.config.filter.AuthenticationFilter;
import com.example.BankingApplication.config.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import pl.pabjan.bankmanagementsystem.config.filter.AuthenticationFilter;
//import pl.pabjan.bankmanagementsystem.config.filter.AuthorizationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final String secret;
    private final long tokenExpirationTime;


    public SecurityConfig(UserDetailsService userDetailsService, @Value("${jwt.secret}") String secret, @Value("${jwt.expirationTime}") long tokenExpirationTime) {
        this.userDetailsService = userDetailsService;
        this.secret = secret;
        this.tokenExpirationTime = tokenExpirationTime;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests().antMatchers("/login", "/api/customer/register")
                .permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManagerBean(), secret, tokenExpirationTime))
                .addFilter(new AuthorizationFilter(authenticationManagerBean(), userDetailsService, secret)).exceptionHandling()
                .and()
                .exceptionHandling();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
