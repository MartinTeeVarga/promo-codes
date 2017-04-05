package com.czequered.promocodes.security;

import com.czequered.promocodes.service.InvalidTokenException;
import com.czequered.promocodes.service.TokenService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;

/**
 * @author Martin Varga
 */
public class AuthenticationTokenFilterTest {

    private TokenService tokenService;
    private AuthenticationTokenFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @Before
    public void before() {
        tokenService = mock(TokenService.class);
        filter = new AuthenticationTokenFilter(tokenService, "/api/**");
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Test
    public void doFilterTest() throws Exception {
        when(request.getServletPath()).thenReturn("/api/v1/user");
        when(request.getMethod()).thenReturn(GET.name());
        filter.doFilter(request, response, filterChain);
        verify(filterChain, atLeastOnce()).doFilter(request, response);
    }

    @Test
    public void doFilterInvalidTokenTest() throws Exception {
        Mockito.doThrow(new InvalidTokenException()).when(tokenService).validateToken(anyString());
        when(request.getServletPath()).thenReturn("/api/v1/user");
        when(request.getMethod()).thenReturn(GET.name());
        filter.doFilter(request, response, filterChain);
        verify(filterChain, never()).doFilter(request, response);
        verify(response, atLeastOnce()).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
    }

    @Test
    public void doFilterOutsideApiUrl() throws Exception {
        when(request.getServletPath()).thenReturn("/login/facebook");
        when(request.getMethod()).thenReturn(GET.name());
        filter.doFilter(request, response, filterChain);
        verify(tokenService, never()).validateToken(anyString());
        verify(filterChain, atLeastOnce()).doFilter(request, response);
    }

    @Test
    public void doFilterOptions() throws Exception {
        when(request.getServletPath()).thenReturn("/api/v1/user");
        when(request.getMethod()).thenReturn(OPTIONS.name());
        filter.doFilter(request, response, filterChain);
        verify(tokenService, never()).validateToken(anyString());
        verify(filterChain, atLeastOnce()).doFilter(request, response);
    }
}