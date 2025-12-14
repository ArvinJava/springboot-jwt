package com.arvin.springbootjwt.config;

import com.arvin.springbootjwt.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url  = request.getRequestURI();
        if("/login".equals(request.getRequestURI())){
            filterChain.doFilter(request,response);
            return;
        }
        String jwtToken = request.getHeader("Authorization");
        if(StringUtils.isBlank(jwtToken)){
            throw  new RuntimeException("jwtToken不存在");
        }
        String email = "";
        try{
            Claims claims = JwtUtil.praseJWT(jwtToken);
            email = claims.getSubject();
        }catch (Exception e){
            throw new RuntimeException("token錯誤");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
}
