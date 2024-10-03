package com.apps.bouncer.security.filter;

import com.apps.bouncer.exceptions.AuthorizationHeaderNotFoundException;
import com.apps.bouncer.exceptions.AuthorizationHeaderNotValidException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

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

    printRequestHeaders(request);

            if(request.getHeader("authorization") == null){
                throw new AuthorizationHeaderNotFoundException("Authorization header not found.");
            }

            if(!request.getHeader("Authorization").startsWith("Bearer")){
                throw new AuthorizationHeaderNotValidException("Authorization header does not start with a bearer token.");
            }

            String token = request.getHeader("Authorization").substring("Bearer ".length());

            try{
                utils.validateToken(token);
                String username = utils.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                                userDetails.getPassword(),
                                userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } catch (Exception exc){
                LOGGER.error(exc.getMessage());
            }

        filterChain.doFilter(request, response);
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.contains("/api/v1/public") || path.contains("/h2-console");
    }



    public void printRequestHeaders(HttpServletRequest req) {
        Enumeration names = req.getHeaderNames();
        if(names == null) {
            return;
        }
        while(names.hasMoreElements()) {
            String name = (String) names.nextElement();
            Enumeration values = req.getHeaders(name);
            if(values != null) {
                while(values.hasMoreElements()) {
                    String value = (String) values.nextElement();
                    System.out.println(name + " : " + value );
                }
            }
        }
    }

}
