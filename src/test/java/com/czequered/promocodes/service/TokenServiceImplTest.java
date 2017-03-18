package com.czequered.promocodes.service;

import org.junit.Before;
import org.junit.Test;

import java.time.Clock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Martin Varga
 */
public class TokenServiceImplTest {

    private static final long EXPIRATION = 1000L;
    private TokenService service;
    private Clock clock;

    @Before
    public void before() {
        clock = mock(Clock.class);
        service = new TokenServiceImpl(EXPIRATION, "secret", () -> clock);
    }

    @Test
    public void getUserIdFromTokenTest() throws Exception {
        String token = service.generateToken("Krtek");
        assertThat(service.getUserIdFromToken(token)).isEqualTo("Krtek");
    }

    @Test
    public void emptyUserIdIsInvalid() throws Exception {
        String token = service.generateToken("");
        assertThatThrownBy(() -> service.getUserIdFromToken(token)).isInstanceOf(InvalidTokenException.class);
    }

    @Test
    public void nullTokenValidateTest() {
        assertThatThrownBy(() -> service.validateToken(null)).isInstanceOf(InvalidTokenException.class);
    }

    @Test
    public void emptyTokenValidateTest() {
        assertThatThrownBy(() -> service.validateToken("")).isInstanceOf(InvalidTokenException.class);
    }

    @Test
    public void validateTokenTest() throws Exception {
        when(clock.millis()).thenReturn(1000000L);
        String token = service.generateToken("Krtek");
        when(clock.millis()).thenReturn(1000500L);
        service.validateToken(token);
        when(clock.millis()).thenReturn(1001500L);
        assertThatThrownBy(() -> service.validateToken(token)).isInstanceOf(InvalidTokenException.class);
    }
}