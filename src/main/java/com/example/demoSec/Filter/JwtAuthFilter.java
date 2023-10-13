package com.example.demoSec.Filter;

import com.example.demoSec.Config.UserInfoToUserDetails;
import com.example.demoSec.Service.JWTService;
import com.example.demoSec.Service.UserInfoDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService ;
    @Autowired
    private UserInfoDetailsService userInfoDetailsService ;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username=null;
        if(authHeader!=null && authHeader.startsWith("Bearer")){
            token = authHeader.split(" ")[1];
             username = jwtService.extractUsername(token);
        }
            if(username!=null ){

                if(SecurityContextHolder.getContext().getAuthentication()==null) {
                    UserDetails user = userInfoDetailsService.loadUserByUsername(username);
                    if (jwtService.validateToken(token, user)) {
                        UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        userAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(userAuthToken);
                    }
                }
            }
                filterChain.doFilter(request,response);
    }

}
