package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Game;
import com.czequered.promocodes.service.ClockService;
import com.czequered.promocodes.service.GameService;
import com.czequered.promocodes.service.TokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author Martin Varga
 */
@ActiveProfiles("resttest")
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

    @Before
    public void before() {
        when(clockService.getClock()).thenReturn(Clock.systemUTC());
    }

    @Test
    public void authenticationPresent() {
        when(gameService.getGame(eq("krtek"), eq("auticko"))).thenReturn(new Game("krtek", "auticko"));

        HttpHeaders headers = new HttpHeaders();
        String token = tokenService.generateToken("krtek");
        headers.set(TOKEN_HEADER, token);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Game> response = restTemplate.exchange("/api/v1/games/auticko", HttpMethod.GET, entity, Game.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Game game = response.getBody();
        assertThat(game).isEqualTo(new Game("krtek", "auticko"));
    }

    @Test
    public void authenticationNotPresent() {
        when(gameService.getGame(eq("krtek"), eq("auticko"))).thenReturn(new Game("krtek", "auticko"));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<Game> response = restTemplate.exchange("/api/v1/games/auticko", HttpMethod.GET, entity, Game.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
