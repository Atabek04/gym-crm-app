package com.epam.gym.api;

import com.epam.gym.config.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@RequiredArgsConstructor
@WebAppConfiguration
class AuthControllerIntegrationTest {
    private final WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    private String getBasicAuthHeader(String password) {
        String credentials = "Man.Super" + ":" + password;
        byte[] base64Credentials = Base64.getEncoder().encode(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(base64Credentials, StandardCharsets.UTF_8);
    }

    @Test
    @Order(1)
    void givenValidCredentials_whenLogin_thenStatusOk() throws Exception {
        mockMvc.perform(get("/v1/auth/login")
                        .header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Man.Super\", \"password\":\"123\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenInvalidCredentials_whenLogin_thenStatusUnauthorized() throws Exception {
        mockMvc.perform(get("/v1/auth/login")
                        .header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("wrong_password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Man.Super\", \"password\":\"wrong_password\"}"))
                .andDo(print())
                .andExpect(status().isIAmATeapot());
    }

    @Test
    @Order(2)
    void givenValidPasswordChange_whenPut_thenStatusOk() throws Exception {
        mockMvc.perform(put("/v1/auth/password")
                        .header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Super.Man2\", \"oldPassword\":\"123\", \"newPassword\":\"123\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenInvalidPasswordChange_whenPut_thenStatusUnauthorized() throws Exception {
        mockMvc.perform(put("/v1/auth/password")
                        .header(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Man.Super\", \"oldPassword\":\"wrong_password\", \"newPassword\":\"new_pass\"}"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
