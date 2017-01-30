package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.Code;
import com.czequered.promocodes.service.CodeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Collections;

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
    private CodeService service;

    private MockMvc mockMvc;

    @Before
    public void before() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void list() throws Exception {
        Code code = new Code();
        code.setGameId("test");
        code.setCodeId("PUB1");
        when(service.getCodes(0)).thenReturn(new PageImpl<>(Collections.singletonList(code)));
        MvcResult result = mockMvc.perform(get("/v1/games/test/codes/list"))
                .andExpect(status().isOk())
                .andReturn();
        Code[] codes = extractCodes(result);
        assertThat(codes).containsExactly(code);
    }

    @Test
    public void listPageDoesNotExist() throws Exception {
        Code code = new Code();
        code.setGameId("test");
        code.setCodeId("PUB1");
        when(service.getCodes(1)).thenReturn(new PageImpl<>(Collections.emptyList()));
        mockMvc.perform(get("/v1/games/test/codes/list?page=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").doesNotExist())
                .andExpect(jsonPath("$.page").exists());
    }

    @Test
    public void listPageTwo() throws Exception {
        Code code = new Code();
        code.setGameId("test");
        code.setCodeId("PUB21");
        when(service.getCodes(1)).thenReturn(new PageImpl<>(Collections.singletonList(code)));
        MvcResult result = mockMvc.perform(get("/v1/games/test/codes/list?page=1"))
                .andExpect(status().isOk())
                .andReturn();
        Code[] codes = extractCodes(result);
        assertThat(codes).containsExactly(code);
    }

    @Test
    public void getCode() throws Exception {
        Code code = new Code();
        code.setGameId("test");
        code.setCodeId("PUB1");
        code.setPayload("Hello");
        when(service.getCode(eq("test"), eq("PUB1"))).thenReturn(code);
        mockMvc.perform(get("/v1/games/test/codes/PUB1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload").value("Hello"))
                .andReturn();
    }

    @Test
    public void getCodeNotFound() throws Exception {
        Code code = new Code();
        code.setGameId("test");
        code.setCodeId("PUB2");
        code.setPayload("Hello");
        when(service.getCode(eq("test"), eq("PUB1"))).thenReturn(null);
        mockMvc.perform(get("/v1/games/test/codes/PUB1"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    private Code[] extractCodes(MvcResult result) throws IOException {
        String contentAsString = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(contentAsString);
        JsonNode listNode = root.at("/_embedded/codeList");
        return mapper.treeToValue(listNode, Code[].class);
    }
}