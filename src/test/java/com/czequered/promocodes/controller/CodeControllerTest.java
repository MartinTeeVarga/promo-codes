package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Code;
import com.czequered.promocodes.model.Game;
import com.czequered.promocodes.service.CodeService;
import com.czequered.promocodes.service.GameService;
import com.czequered.promocodes.service.TokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Collections;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.eq;
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
public class CodeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CodeService codeService;

    @Autowired
    private GameService gameService;

    @Autowired
    private TokenService tokenService;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void list() throws Exception {
        Code code = new Code();
        code.setGameId("auticko");
        code.setCodeId("PUB1");
        String token = tokenService.generateToken("Krtek");
        when(gameService.getGame(eq("Krtek"), eq("auticko"))).thenReturn(new Game("Krtek", "auticko"));
        when(codeService.getCodes("auticko")).thenReturn(Collections.singletonList(code));
        MvcResult result = mockMvc.perform(get("/api/v1/games/auticko/codes/list").header(TOKEN_HEADER, token))
                .andExpect(status().isOk())
                .andReturn();
        Code[] codes = extractCodes(result);
        assertThat(codes).containsExactly(code);
    }

    @Test
    public void getCode() throws Exception {
        Code code = new Code();
        code.setGameId("auticko");
        code.setCodeId("PUB1");
        code.setPayload("Hello");
        String token = tokenService.generateToken("Krtek");
        when(gameService.getGame(eq("Krtek"), eq("auticko"))).thenReturn(new Game("Krtek", "auticko"));
        when(codeService.getCode(eq("auticko"), eq("PUB1"))).thenReturn(code);
        mockMvc.perform(get("/api/v1/games/auticko/codes/PUB1").header(TOKEN_HEADER, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload").value("Hello"))
                .andReturn();
    }

    @Test
    public void getCodeNotFound() throws Exception {
        Code code = new Code();
        code.setGameId("auticko");
        code.setCodeId("PUB2");
        code.setPayload("Hello");
        String token = tokenService.generateToken("Krtek");
        when(gameService.getGame(eq("Krtek"), eq("auticko"))).thenReturn(new Game("Krtek", "auticko"));
        when(codeService.getCode(eq("auticko"), eq("PUB1"))).thenReturn(null);
        mockMvc.perform(get("/api/v1/games/auticko/codes/PUB1").header(TOKEN_HEADER, token))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    private Code[] extractCodes(MvcResult result) throws IOException {
        String contentAsString = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(contentAsString);
        return mapper.treeToValue(root, Code[].class);
    }
}