package com.example.tour.authentication.config;

import com.example.tour.authentication.filter.AuthenticationConfigConstants;
import com.example.tour.authentication.filter.JWTAuthenticationFilter;
import com.example.tour.authentication.filter.JWTAuthorizationFilter;
import com.example.tour.authentication.service.AuthenticationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserService authenticationUserService;

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, AuthenticationConfigConstants.SIGN_UP_URL).permitAll().antMatchers("/user/**").hasAnyAuthority("ADMIN").anyRequest().authenticated().and().addFilter(new JWTAuthenticationFilter(authenticationManager())).addFilter(new JWTAuthorizationFilter(authenticationManager())).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(authenticationUserService).passwordEncoder(bCryptPasswordEncoder);
    }
}
