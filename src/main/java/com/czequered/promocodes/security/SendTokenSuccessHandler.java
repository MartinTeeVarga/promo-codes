package com.czequered.promocodes.security;

import com.czequered.promocodes.config.Constants;
import com.czequered.promocodes.model.User;
import com.czequered.promocodes.service.TokenService;
import com.czequered.promocodes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;

/**
 * @author Martin Varga
 */
@Component
public class SendTokenSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private TokenService tokenService;

    private UserService userService;

    private String corsUrl;
    private String githubEndpoint;
    private String facebookEndpoint;

    @Autowired
    public SendTokenSuccessHandler(TokenService tokenService, UserService userService,
                                   @Value("${jepice.frontend.url:none}") String corsUrl,
                                   @Value("${github.endpoint:none}") String githubEndpoint,
                                   @Value("${facebook.endpoint:none}") String facebookEndpoint) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.corsUrl = corsUrl;
        this.githubEndpoint = githubEndpoint;
        this.facebookEndpoint = facebookEndpoint;
    }

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect.");
            return;
        }

        String servletPath = request.getServletPath();
        if (servletPath.equals(githubEndpoint)) {
            createTokenAndContinue(request, response, authentication, Constants.GITHUB_PREFIX);
        } else if (servletPath.equals(facebookEndpoint)) {
            createTokenAndContinue(request, response, authentication, Constants.FACEBOOK_PREFIX);
        } else {
            logger.debug("Invalid auth endpoint: " + servletPath);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Wrong authentication endpoint.");
        }
    }

    private void createTokenAndContinue(HttpServletRequest request, HttpServletResponse response, Authentication authentication, String prefix) throws IOException {
        String name = prefix + authentication.getName();

        User user = userService.getUser(name);
        if (user == null) {
            user = new User(name);
            userService.saveUser(user);
        }

        String token = tokenService.generateToken(name);
        response.addCookie(new Cookie(TOKEN_HEADER, token));
        response.addHeader(TOKEN_HEADER, token);
        getRedirectStrategy().sendRedirect(request, response, corsUrl);
    }

}