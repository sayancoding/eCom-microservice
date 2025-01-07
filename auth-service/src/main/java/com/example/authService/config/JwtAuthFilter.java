package com.example.authService.config;

import com.example.authService.entity.CustomUserDetails;
import com.example.authService.service.CustomUserDetailsService;
import com.example.authService.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response); //bypass to username-password filter
        }

        final String jwtToken = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwtToken);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(username != null && authentication == null){
            //authentication operation..
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if(jwtService.isTokenValid(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //creating sessionId on security-context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request,response); //bypass to username-password filter
    }
}
