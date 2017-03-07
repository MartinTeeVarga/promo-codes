package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Game;
import com.czequered.promocodes.service.ClockService;
import com.czequered.promocodes.service.GameService;
import com.czequered.promocodes.service.TokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.time.Clock;
import java.util.Collections;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Martin Varga
 */
@ActiveProfiles("resttest")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GameService gameService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClockService clockService;

    @Value("${jepice.jwt.expiry}")
    private long expiry;

    private MockMvc mockMvc;
    private Clock clock;

    @Before
    public void before() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        clock = mock(Clock.class);
        when(clockService.getClock()).thenReturn(clock);
    }

    @Test
    public void listTest() throws Exception {
        Game game = new Game("krtek", "auticko");
        when(gameService.getGames(eq("krtek"))).thenReturn(Collections.singletonList(game));
        String token = tokenService.generateToken("krtek");
        MvcResult result = mockMvc.perform(get("/api/v1/games/list").header(TOKEN_HEADER, token))
                .andExpect(status().isOk())
                .andReturn();
        Game[] games = extractGames(result);
        assertThat(games).containsExactly(game);
    }

    @Test
    public void listInvalidTokenTest() throws Exception {
        Game game = new Game("krtek", "auticko");
        when(clock.millis()).thenReturn(10000000L);
        when(gameService.getGames(eq("krtek"))).thenReturn(Collections.singletonList(game));
        String token = tokenService.generateToken("krtek");
        when(clock.millis()).thenReturn(10000000L + expiry + 1);
        mockMvc.perform(get("/api/v1/games/list").header(TOKEN_HEADER, token))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    public void getGameNotFoundTest() throws Exception {
        when(gameService.getGame(eq("krtek"), eq("auticko"))).thenReturn(null);
        String token = tokenService.generateToken("krtek");
        mockMvc.perform(get("/api/v1/games/auticko").header(TOKEN_HEADER, token))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getGameInvalidTokenTest() throws Exception {
        Game game = new Game("krtek", "auticko");
        when(clock.millis()).thenReturn(10000000L);
        when(gameService.getGame(eq("krtek"), eq("auticko"))).thenReturn(game);
        String token = tokenService.generateToken("krtek");
        when(clock.millis()).thenReturn(10000000L + expiry + 1);
        mockMvc.perform(get("/api/v1/games/auticko").header(TOKEN_HEADER, token))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    public void getGameTest() throws Exception {
        Game game = new Game("krtek", "auticko");
        game.setDetails("Ahoj");
        when(gameService.getGame(eq("krtek"), eq("auticko"))).thenReturn(game);
        String token = tokenService.generateToken("krtek");
        mockMvc.perform(get("/api/v1/games/auticko").header(TOKEN_HEADER, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.details").value("Ahoj"))
                .andReturn();
    }

    private Game[] extractGames(MvcResult result) throws IOException {
        String contentAsString = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(contentAsString);
        return mapper.treeToValue(root, Game[].class);
    }
}