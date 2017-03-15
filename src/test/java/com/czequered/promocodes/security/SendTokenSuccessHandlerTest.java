package com.czequered.promocodes.security;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletResponse;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Martin Varga
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SendTokenSuccessHandlerTest {
    @Value("${jepice.frontend.url:none}")
    String corsUrl;

    @Autowired
    SendTokenSuccessHandler handler;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Authentication authentication;
    private RedirectStrategy redirectStrategy;

    @Before
    public void before() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        authentication = mock(Authentication.class);
        redirectStrategy = mock(RedirectStrategy.class);
        handler.setRedirectStrategy(redirectStrategy);
    }

    @Test
    public void handle() throws Exception {
        when(response.isCommitted()).thenReturn(false);
        when(authentication.getName()).thenReturn("Krtek");
        handler.handle(request, response, authentication);

        verify(response, atLeastOnce()).addHeader(eq(TOKEN_HEADER), anyString());
        verify(redirectStrategy, atLeastOnce()).sendRedirect(request, response, corsUrl);
    }

    @Test
    public void handleAlreadyRedirected() throws Exception {
        when(response.isCommitted()).thenReturn(true);
        handler.handle(request, response, authentication);

        verify(response, never()).addHeader(eq(TOKEN_HEADER), anyString());
        verify(redirectStrategy, never()).sendRedirect(request, response, corsUrl);
    }

}