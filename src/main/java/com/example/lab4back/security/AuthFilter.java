package com.example.lab4back.security;

import com.example.lab4back.service.auth.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtUtils jwtUtils;

    private final String headerMetaInf = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String username;
        final String jwt;
        final String headerAuthorization = request.getHeader("Authorization");
        try {
            if (StringUtils.hasText(headerAuthorization) && headerAuthorization.startsWith(headerMetaInf)) {
                jwt = headerAuthorization.substring(headerMetaInf.length(), headerAuthorization.length());
            } else {
                jwt = null;
            }
            if (jwt != null && jwtUtils.validateToken(jwt)) {
                username = jwtUtils.extractUsername(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception ignore) { }

        filterChain.doFilter(request, response);
    }
}
