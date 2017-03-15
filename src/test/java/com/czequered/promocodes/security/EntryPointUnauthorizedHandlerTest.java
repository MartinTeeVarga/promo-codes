package com.czequered.promocodes.security;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

/**
 * @author Martin Varga
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EntryPointUnauthorizedHandlerTest {

    @Autowired
    EntryPointUnauthorizedHandler handler;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private AuthenticationException authenticationException;

    @Before
    public void before() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        authenticationException = mock(AuthenticationException.class);
    }

    @Test
    public void commenceTest() throws Exception {
        handler.commence(request, response, authenticationException);
        verify(response, atLeastOnce()).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
    }
}