package com.czequered.promocodes.security;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;

/**
 * @author Martin Varga
 */
@SpringBootTest
public class SendTokenSuccessHandlerTest {

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
//        when(response.isCommitted()).thenReturn(false);
//        when(authentication.getName()).thenReturn("Krtek");
//
//        handler.handle(request, response, authentication);
    }

}