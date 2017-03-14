package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Game;
import com.czequered.promocodes.service.GameService;
import com.czequered.promocodes.service.TokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Collections;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Before
    public void before() {
        mapper = new ObjectMapper();
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void after() {
        reset(gameService);
    }

    @Test
    public void listTest() throws Exception {
        Game game = new Game("Krtek", "auticko");
        when(gameService.getGames(eq("Krtek"))).thenReturn(Collections.singletonList(game));
        String token = tokenService.generateToken("Krtek");
        MvcResult result = mockMvc.perform(get("/api/v1/games/list").header(TOKEN_HEADER, token))
                .andExpect(status().isOk())
                .andReturn();
        Game[] games = extractGames(result);
        assertThat(games).containsExactly(game);
    }

    @Test
    public void getGameNotFoundTest() throws Exception {
        when(gameService.getGame(eq("Krtek"), eq("auticko"))).thenReturn(null);
        String token = tokenService.generateToken("Krtek");
        mockMvc.perform(get("/api/v1/games/auticko").header(TOKEN_HEADER, token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getGameTest() throws Exception {
        Game game = new Game("Krtek", "auticko");
        game.setDetails("Ahoj");
        when(gameService.getGame(eq("Krtek"), eq("auticko"))).thenReturn(game);
        String token = tokenService.generateToken("Krtek");
        mockMvc.perform(get("/api/v1/games/auticko").header(TOKEN_HEADER, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.details").value("Ahoj"));
    }

    @Test
    public void saveNewGame() throws Exception {
        Game game = new Game("Krtek", null);
        game.setDetails("Ahoj");
        Game saved = new Game(game.getUserId(), "auticko");
        saved.setDetails(game.getDetails());
        String token = tokenService.generateToken("Krtek");
        when(gameService.saveGame(eq(game))).thenReturn(saved);
        String json = mapper.writeValueAsString(game);
        mockMvc.perform(post("/api/v1/games")
                .header(TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value("auticko"));
    }

    @Test
    public void saveNewGameWithIdSet() throws Exception {
        Game game = new Game("Krtek", "auticko");
        String json = mapper.writeValueAsString(game);
        String token = tokenService.generateToken("Krtek");
        mockMvc.perform(post("/api/v1/games")
                .header(TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
        verify(gameService, never()).saveGame(any(Game.class));
    }

    @Test
    public void saveExistingGame() throws Exception {
        Game game = new Game("Krtek", "auticko");
        game.setDetails("Ahoj");
        when(gameService.saveGame(eq(game))).thenReturn(game);
        String json = mapper.writeValueAsString(game);
        String token = tokenService.generateToken("Krtek");
        mockMvc.perform(put("/api/v1/games")
                .header(TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value("auticko"));
    }

    @Test
    public void saveExistingGameWithNullId() throws Exception {
        Game game = new Game("Krtek", null);
        String json = mapper.writeValueAsString(game);
        mockMvc.perform(put("/api/v1/games").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
        verify(gameService, never()).saveGame(any(Game.class));
    }

    @Test
    public void saveNewGameSpoofing() throws Exception {
        Game game = new Game("Sova", null);
        String token = tokenService.generateToken("Krtek");
        String json = mapper.writeValueAsString(game);
        mockMvc.perform(post("/api/v1/games")
                .header(TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
        verify(gameService, never()).saveGame(any(Game.class));
    }

    @Test
    public void saveExistingGameSpoofing() throws Exception {
        Game game = new Game("Sova", "auticko");
        String token = tokenService.generateToken("Krtek");
        String json = mapper.writeValueAsString(game);
        mockMvc.perform(put("/api/v1/games")
                .header(TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
        verify(gameService, never()).saveGame(any(Game.class));
    }

    @Test
    public void deleteGame() throws Exception {
        String token = tokenService.generateToken("Krtek");
        mockMvc.perform(delete("/api/v1/games/auticko").header(TOKEN_HEADER, token))
                .andExpect(status().isOk());
        verify(gameService, only()).deleteGame(eq("Krtek"), eq("auticko"));
    }

    private Game[] extractGames(MvcResult result) throws IOException {
        String contentAsString = result.getResponse().getContentAsString();
        JsonNode root = mapper.readTree(contentAsString);
        return mapper.treeToValue(root, Game[].class);
    }
}