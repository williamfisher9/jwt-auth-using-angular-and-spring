package com.apps.bouncer.security.filter;

import com.apps.bouncer.exceptions.AuthorizationHeaderNotFoundException;
import com.apps.bouncer.exceptions.AuthorizationHeaderNotValidException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils utils;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(JwtUtils utils, UserDetailsService userDetailsService) {
        this.utils = utils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

       // if(authenticateResource(request)){
            if(request.getHeader("Authorization") == null){
                throw new AuthorizationHeaderNotFoundException("Authorization header not found.");
            }

            if(!request.getHeader("Authorization").startsWith("Bearer")){
                throw new AuthorizationHeaderNotValidException("Authorization header does not start with a bearer token.");
            }

            String token = request.getHeader("Authorization").substring("Bearer ".length());

            utils.validateToken(token);



            String username = utils.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                            userDetails.getPassword(),
                            userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);



        //}

        filterChain.doFilter(request, response);
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.contains("/api/v1/auth") || path.contains("/h2-console");
    }

    /*private boolean authenticateResource(HttpServletRequest request){
        if(request.getRequestURI().contains("h2-console") || request.getRequestURI().contains("auth")){
            return false;
        }

        return true;
    }*/
}
