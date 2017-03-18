package com.czequered.promocodes.security;

import com.czequered.promocodes.config.Constants;
import com.czequered.promocodes.service.InvalidTokenException;
import com.czequered.promocodes.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationTokenFilter extends GenericFilterBean {

    private TokenService tokenService;

    private final AntPathRequestMatcher antPathRequestMatcher;

    @Autowired
    public AuthenticationTokenFilter(TokenService tokenService,
                                     @Value("${jepice.api}") String apiPattern) {
        this.tokenService = tokenService;
        antPathRequestMatcher = new AntPathRequestMatcher(apiPattern);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            if (antPathRequestMatcher.matches(httpRequest)) {
                String authToken = httpRequest.getHeader(Constants.TOKEN_HEADER);
                tokenService.validateToken(authToken);
            }
            chain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            logger.debug("Invalid token, IP: " + request.getRemoteHost(), e);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
        }
    }
}
