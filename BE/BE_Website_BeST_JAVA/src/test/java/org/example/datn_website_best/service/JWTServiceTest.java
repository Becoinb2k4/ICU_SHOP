package org.example.datn_website_best.service;

import org.example.datn_website_best.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JWTServiceTest {

    private static final String BASE64_SECRET = "MDEyMzQ1Njc4OUFCQ0RFRjAxMjM0NTY3ODlBQkNERUY=";

    @Test
    void generate_token_and_extract_subject() {
        JWTService jwtService = buildService(1L);
        Account account = Account.builder().email("user@example.com").build();

        String token = jwtService.generateToken(account);

        assertNotNull(token);
        assertEquals("user@example.com", jwtService.extract(token));
        assertTrue(jwtService.isValid(token, account));
    }

    @Test
    void is_valid_false_when_expired() {
        JWTService jwtService = buildService(-1L);
        Account account = Account.builder().email("user@example.com").build();

        String token = jwtService.generateToken(account);

        assertFalse(jwtService.isValid(token, account));
    }

    private JWTService buildService(Long expirationHours) {
        JWTService jwtService = new JWTService();
        ReflectionTestUtils.setField(jwtService, "secret", BASE64_SECRET);
        ReflectionTestUtils.setField(jwtService, "expiration", expirationHours);
        return jwtService;
    }
}
