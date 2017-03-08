package com.czequered.promocodes.controller;

import com.czequered.promocodes.model.User;
import com.czequered.promocodes.service.ClockService;
import com.czequered.promocodes.service.TokenService;
import com.czequered.promocodes.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.Clock;

import static com.czequered.promocodes.config.Constants.TOKEN_HEADER;
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
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

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
    public void getUser() throws Exception {
        User user = new User("Krtek");
        user.setDetails("Mys");
        when(userService.getUser(eq("Krtek"))).thenReturn(user);
        when(clock.millis()).thenReturn(10000000L);
        String token = tokenService.generateToken("Krtek");
        mockMvc.perform(get("/api/v1/user").header(TOKEN_HEADER, token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.details").value("Mys"));
    }


    @Test
    public void getUserInvalidTokenTest() throws Exception {
        User user = new User("Krtek");
        user.setDetails("Mys");
        when(userService.getUser(eq("Krtek"))).thenReturn(user);
        when(clock.millis()).thenReturn(10000000L);
        String token = tokenService.generateToken("Krtek");
        when(clock.millis()).thenReturn(10000000L + expiry + 1);
        mockMvc.perform(get("/api/v1/user").header(TOKEN_HEADER, token))
            .andExpect(status().isForbidden());
    }
}