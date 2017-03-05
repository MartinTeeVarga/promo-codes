package com.czequered.promocodes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
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
public class UrlParameterAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${cors.url:none}")
    String corsUrl;

    @Autowired
    AuthStore store;

    @Autowired
    TokenUtils tokenUtils;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect.");
            return;
        }
        System.out.println("authentication = " + authentication);
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenType = details.getTokenType();
        System.out.println("tokenType = " + tokenType);
        String tokenValue = details.getTokenValue();
        Object decodedDetails = details.getDecodedDetails();
        System.out.println("decodedDetails = " + decodedDetails);
        System.out.println("tokenValue = " + tokenValue);
        System.out.println("authentication = " + details);
        System.out.println("authentication = " + authentication.getDetails().getClass());
        System.out.println("authentication = " + authentication.getCredentials());

        store.put("lala", authentication);

        String bob = tokenUtils.generateToken("bob");
        System.out.println("bob = " + bob);


        getRedirectStrategy().sendRedirect(request, response, "http://localhost:8080?token=lala");
    }

}