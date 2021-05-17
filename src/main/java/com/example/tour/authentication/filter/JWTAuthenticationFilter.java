package com.example.tour.authentication.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.tour.authentication.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException{
        try{
            User credentials = new ObjectMapper().readValue(httpServletRequest.getInputStream(),User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(),credentials.getPassword(),new ArrayList<>()));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, FilterChain filterChain, Authentication authentication) throws IOException, ServletException{
        String token = JWT.create().withSubject(((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername()).withClaim("role",authentication.getAuthorities().iterator().next().getAuthority()).withExpiresAt(new Date(System.currentTimeMillis()+AuthenticationConfigConstants.EXPIRATION_TIME)).sign(Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.getBytes()));
        httpServletResponse.addHeader(AuthenticationConfigConstants.HEADER_STRING,AuthenticationConfigConstants.TOKEN_PREFIX+token);
        httpServletResponse.getWriter().write("["+objectMapper.writeValueAsString(authentication.getPrincipal())+",{\"token\": \""+AuthenticationConfigConstants.TOKEN_PREFIX + token + "\"}]");
    }
}
