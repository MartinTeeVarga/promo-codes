package com.czequered.promocodes.security;

import com.czequered.promocodes.service.ClockService;
import com.czequered.promocodes.service.TokenService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletResponse;
import java.time.Clock;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Martin Varga
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("filtertest")
public class AuthenticationTokenFilterTest {

    @Value("${jepice.jwt.expiry}")
    private long expiry;

    @Autowired
    AuthenticationTokenFilter filter;

    @Autowired
    ClockService clockService;

    @Autowired
    TokenService tokenService;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private SecurityContext securityContext;
    private Clock clock;

    @Before
    public void before() {
        clock = mock(Clock.class);
        when(clock.millis()).thenReturn(System.currentTimeMillis());
        when(clockService.getClock()).thenReturn(clock);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void doFilterTest() throws Exception {
        String token = tokenService.generateToken("Krtek");
        when(request.getHeader(TOKEN_HEADER)).thenReturn(token);
        when(securityContext.getAuthentication()).thenReturn(null);
        filter.doFilter(request, response, filterChain);
        ArgumentCaptor<Authentication> authCaptor = ArgumentCaptor.forClass(Authentication.class);
        verify(securityContext, atLeastOnce()).setAuthentication(authCaptor.capture());
        assertThat(authCaptor.getValue().getName()).isEqualTo("Krtek");
        verify(filterChain, atLeastOnce()).doFilter(request, response);
    }

    @Test
    public void doFilterInvalidTokenTest() throws Exception {
        when(request.getHeader(TOKEN_HEADER)).thenReturn("Krtek");
        when(securityContext.getAuthentication()).thenReturn(null);
        filter.doFilter(request, response, filterChain);
        verify(securityContext, never()).setAuthentication(any(Authentication.class));
        verify(filterChain, never()).doFilter(request, response);
        verify(response, atLeastOnce()).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
    }

    @Test
    public void doFilterExpiredTokenTest() throws Exception {
        String token = tokenService.generateToken("Krtek");
        when(request.getHeader(TOKEN_HEADER)).thenReturn(token);
        when(securityContext.getAuthentication()).thenReturn(null);
        when(clock.millis()).thenReturn(System.currentTimeMillis() + expiry + 1);
        filter.doFilter(request, response, filterChain);
        verify(securityContext, never()).setAuthentication(any(Authentication.class));
        verify(filterChain, never()).doFilter(request, response);
        verify(response, atLeastOnce()).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
    }

    @Test
    public void doFilterAuthenticatedTest() throws Exception {
        String token = tokenService.generateToken("Krtek");
        when(request.getHeader(TOKEN_HEADER)).thenReturn(token);
        when(securityContext.getAuthentication()).thenReturn(mock(Authentication.class));
        filter.doFilter(request, response, filterChain);
        verify(securityContext, never()).setAuthentication(any(Authentication.class));
        verify(filterChain, atLeastOnce()).doFilter(request, response);
    }
}