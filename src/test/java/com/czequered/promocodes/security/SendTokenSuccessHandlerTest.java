package com.czequered.promocodes.security;

import com.czequered.promocodes.model.User;
import com.czequered.promocodes.service.TokenService;
import com.czequered.promocodes.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletResponse;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Martin Varga
 */
public class SendTokenSuccessHandlerTest {
    private String corsUrl = "http://localhost:8090";
    private SendTokenSuccessHandler handler;
    private TokenService tokenService;
    private UserService userService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Authentication authentication;
    private RedirectStrategy redirectStrategy;

    @Before
    public void before() {
        tokenService = mock(TokenService.class);
        userService = mock(UserService.class);
        handler = new SendTokenSuccessHandler(tokenService, userService,
                "http://localhost:8090",
                "/login/github", "/login/facebook");
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        authentication = mock(Authentication.class);
        redirectStrategy = mock(RedirectStrategy.class);
        handler.setRedirectStrategy(redirectStrategy);
    }

    @Test
    public void handleGithubUserExists() throws Exception {
        when(tokenService.generateToken(eq("GITHUB-Krtek"))).thenReturn("Token");
        when(response.isCommitted()).thenReturn(false);
        when(authentication.getName()).thenReturn("Krtek");
        when(request.getServletPath()).thenReturn("/login/github");
        when(userService.getUser(eq("GITHUB-Krtek"))).thenReturn(new User("GITHUB-Krtek"));
        handler.handle(request, response, authentication);

        ArgumentCaptor<String> headerCaptor = ArgumentCaptor.forClass(String.class);
        verify(response, atLeastOnce()).addHeader(eq(TOKEN_HEADER), eq("Token"));
        verify(redirectStrategy, atLeastOnce()).sendRedirect(request, response, corsUrl);
        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    public void handleFacebookUserDoesNotExist() throws Exception {
        when(tokenService.generateToken(eq("FACEBOOK-Krtek"))).thenReturn("Token");
        when(response.isCommitted()).thenReturn(false);
        when(authentication.getName()).thenReturn("Krtek");
        when(request.getServletPath()).thenReturn("/login/facebook");
        when(userService.getUser(eq("FACEBOOK-Krtek"))).thenReturn(null);
        handler.handle(request, response, authentication);

        verify(response, atLeastOnce()).addHeader(eq(TOKEN_HEADER), eq("Token"));
        verify(redirectStrategy, atLeastOnce()).sendRedirect(request, response, corsUrl);
        verify(userService, atLeastOnce()).saveUser(eq(new User("FACEBOOK-Krtek")));
    }

    @Test
    public void handleUnknown() throws Exception {
        when(response.isCommitted()).thenReturn(false);
        when(request.getServletPath()).thenReturn("/login/foo");
        handler.handle(request, response, authentication);

        ArgumentCaptor<String> headerCaptor = ArgumentCaptor.forClass(String.class);
        verify(response, atLeastOnce()).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
        verify(redirectStrategy, never()).sendRedirect(request, response, corsUrl);
    }

    @Test
    public void handlerAlreadyRedirected() throws Exception {
        when(response.isCommitted()).thenReturn(true);
        handler.handle(request, response, authentication);

        verify(response, never()).addHeader(eq(TOKEN_HEADER), anyString());
        verify(redirectStrategy, never()).sendRedirect(request, response, corsUrl);
    }
}