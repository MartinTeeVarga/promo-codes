package com.czequered.promocodes.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author Martin Varga
 */
public class FrontEndTokenAuthFilter extends AbstractAuthenticationProcessingFilter {

    private static final String FILTER_APPLIED = "__FrontEndTokenAuthFilter_applied";

    public FrontEndTokenAuthFilter(AuthStore store, String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        this.store = store;
    }

    private final String HEADER_SECURITY_TOKEN = "X-Token";
    private String token = "";

    AuthStore store;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        this.token = request.getHeader(HEADER_SECURITY_TOKEN);

        //If we have already applied this filter - not sure how that would happen? - then just continue chain
        if (request.getAttribute(FILTER_APPLIED) != null) {
            chain.doFilter(request, response);
            return;
        }

        //Now mark request as completing this filter
        request.setAttribute(FILTER_APPLIED, Boolean.TRUE);

        //Attempt to authenticate
        Authentication authResult;
        authResult = attemptAuthentication(request, response);
        if (authResult == null) {
//            unsuccessfulAuthentication(request, response, new LockedException("Forbidden"));
            chain.doFilter(request, response);
        } else {
            successfulAuthentication(request, response, chain, authResult);
        }
    }

    /**
     * Attempt to authenticate request - basically just pass over to another method to authenticate request headers
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return store.get(token);
    }


//    /**
//     * authenticate the user based on token, mobile app secret & user agent
//     *
//     * @return
//     */
//    private AbstractAuthenticationToken authUserByToken() {
//        AbstractAuthenticationToken authToken = null;
//        try {
//            return store.get(token);
//        } catch (Exception e) {
//            logger.error("Authenticate user by token error: ", e);
//        }
//        return authToken;
//    }
}