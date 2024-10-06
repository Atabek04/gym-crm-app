package com.epam.gym.api;

import com.epam.gym.config.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ApplicationConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerIntegrationTest {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8090/v1/auth";
    private static final String VALID_USERNAME = "Man.Super";
    private static final String VALID_PASSWORD = "123";

    private String getBasicAuthHeader() {
        String credentials = AuthControllerIntegrationTest.VALID_USERNAME + ":" + AuthControllerIntegrationTest.VALID_PASSWORD;
        byte[] base64Credentials = Base64.getEncoder().encode(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(base64Credentials, StandardCharsets.UTF_8);
    }

    private HttpHeaders createHeadersWithAuth() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, getBasicAuthHeader());
        return headers;
    }

    @Test
    @Order(1)
    void givenValidCredentials_whenLogin_thenStatusOk() {
        String requestBody = """
                {
                    "username": "Man.Super",
                    "password": "123"
                }
                """;

        HttpHeaders headers = createHeadersWithAuth();
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/login",
                HttpMethod.POST, requestEntity, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    @Order(2)
    void givenInvalidCredentials_whenLogin_thenStatusUnauthorized() {
        String requestBody = """
                {
                    "username": "Man.Super",
                    "password": "wrong_password"
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            restTemplate.exchange(BASE_URL + "/login", HttpMethod.POST, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode().value()).isEqualTo(418);
            assertThat(e.getResponseBodyAsString()).isEqualTo("Wrong password or username");
        }
    }

    @Test
    @Order(3)
    void givenValidPasswordChange_whenPut_thenStatusOk() {
        String requestBody = """
                {
                    "username": "Super.Man2",
                    "oldPassword": "123",
                    "newPassword": "123"
                }
                """;

        HttpHeaders headers = createHeadersWithAuth();
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/password",
                HttpMethod.PUT, requestEntity, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    @Order(4)
    void givenInvalidPasswordChange_whenPut_thenStatusUnauthorized() {
        String requestBody = """
                {
                    "username": "Man.Super",
                    "oldPassword": "wrong_password",
                    "newPassword": "new_pass"
                }
                """;

        HttpHeaders headers = createHeadersWithAuth();
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            restTemplate.exchange(BASE_URL + "/password", HttpMethod.PUT, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode().value()).isEqualTo(401);
        }
    }
}