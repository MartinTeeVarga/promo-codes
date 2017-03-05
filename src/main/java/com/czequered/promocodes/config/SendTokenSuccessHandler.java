package com.czequered.promocodes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Martin Varga
 */
@Component
public class SendTokenSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${jepice.jwt.header}")
    private String tokenHeader = "X-Token";

    @Value("${jepice.frontend.url:none}")
    String corsUrl;

    @Autowired
    TokenUtils tokenUtils;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect.");
            return;
        }
        String name = authentication.getName();
        String token = tokenUtils.generateToken(name);

        response.addHeader(tokenHeader, token);
        getRedirectStrategy().sendRedirect(request, response, "http://localhost:8080");
    }

}