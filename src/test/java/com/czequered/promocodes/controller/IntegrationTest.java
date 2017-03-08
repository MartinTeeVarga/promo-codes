package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Game;
import com.czequered.promocodes.service.ClockService;
import com.czequered.promocodes.service.GameService;
import com.czequered.promocodes.service.TokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Martin Varga
 */
@ActiveProfiles("integrationtest")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClockService clockService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${jepice.jwt.expiry}")
    private long expiry;

    private Clock clock;

    @Before
    public void before() {
        clock = mock(Clock.class);
        when(clockService.getClock()).thenReturn(clock);
    }

    @Test
    public void authenticationPresent() {
        when(gameService.getGame(eq("Krtek"), eq("auticko"))).thenReturn(new Game("Krtek", "auticko"));

        HttpHeaders headers = new HttpHeaders();
        when(clock.millis()).thenReturn(System.currentTimeMillis());
        String token = tokenService.generateToken("Krtek");
        headers.set(TOKEN_HEADER, token);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Game> response = restTemplate.exchange("/api/v1/games/auticko", HttpMethod.GET, entity, Game.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Game game = response.getBody();
        assertThat(game).isEqualTo(new Game("Krtek", "auticko"));
    }

    @Test
    public void authenticationNotPresent() {
        when(gameService.getGame(eq("Krtek"), eq("auticko"))).thenReturn(new Game("Krtek", "auticko"));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Game> response = restTemplate.exchange("/api/v1/games/auticko", HttpMethod.GET, entity, Game.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void tokenExpired() {
        when(gameService.getGame(eq("Krtek"), eq("auticko"))).thenReturn(new Game("Krtek", "auticko"));

        HttpHeaders headers = new HttpHeaders();
        when(clock.millis()).thenReturn(System.currentTimeMillis());
        String token = tokenService.generateToken("Krtek");
        headers.set(TOKEN_HEADER, token);
        HttpEntity entity = new HttpEntity(headers);

        when(clock.millis()).thenReturn(System.currentTimeMillis() + expiry + 1);
        ResponseEntity<Game> response = restTemplate.exchange("/api/v1/games/auticko", HttpMethod.GET, entity, Game.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
