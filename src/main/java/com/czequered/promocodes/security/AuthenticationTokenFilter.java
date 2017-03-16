package com.czequered.promocodes.security;

import com.czequered.promocodes.config.Constants;
import com.czequered.promocodes.service.InvalidTokenException;
import com.czequered.promocodes.service.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private TokenServiceImpl tokenService;

    private String url;

    public AuthenticationTokenFilter(String url) {
        this.url = url;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {

            if (((HttpServletRequest) request).getRequestURI().contains(url)) {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                String authToken = httpRequest.getHeader(Constants.TOKEN_HEADER);
//                String username = tokenService.getUserIdFromToken(authToken);
//                UserDetails userDetails = new User(username, "", Collections.emptyList());
                tokenService.validateToken(authToken);
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            logger.debug("Invalid token, IP: " + request.getRemoteHost(), e);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
        }
    }
}
