package org.example.datn_website_best.service;

import org.example.datn_website_best.Enum.Status;
import org.example.datn_website_best.dto.request.LoginRequest;
import org.example.datn_website_best.model.Account;
import org.example.datn_website_best.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    PasswordEncoderService passwordEncoderService;

    @Mock
    EmailService emailService;

    @Mock
    JWTService jwtService;

    @InjectMocks
    AuthenticationService authenticationService;

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void authenticate_success_returns_token() {
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password");
        Account account = Account.builder().email("user@example.com").build();

        when(accountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(account));
        when(jwtService.generateToken(account)).thenReturn("token-123");

        var response = authenticationService.authenticate(loginRequest);

        assertNotNull(response);
        assertEquals("token-123", response.getAccessToken());
        assertEquals("1234", response.getResfreshToken());
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("user@example.com", "password")
        );
    }

    @Test
    void authenticate_user_not_found_throws() {
        LoginRequest loginRequest = new LoginRequest("missing@example.com", "password");
        when(accountRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authenticationService.authenticate(loginRequest));
        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    void authenticate_bad_credentials_throws() {
        LoginRequest loginRequest = new LoginRequest("user@example.com", "wrong");
        Account account = Account.builder().email("user@example.com").build();
        when(accountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(account));
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("bad"));

        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(loginRequest));
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void register_success_sets_defaults_and_saves() {
        Account account = Account.builder().email("new@example.com").password("raw").build();
        when(accountRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(passwordEncoderService.encodedPassword("raw")).thenReturn("encoded");
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account saved = authenticationService.register(account);

        assertEquals("USER", saved.getRole());
        assertEquals(Status.ACTIVE.toString(), saved.getStatus());
        assertEquals("encoded", saved.getPassword());
        verify(accountRepository).save(account);
    }

    @Test
    void register_duplicate_email_throws() {
        Account account = Account.builder().email("dup@example.com").build();
        when(accountRepository.findByEmail("dup@example.com")).thenReturn(Optional.of(account));

        assertThrows(RuntimeException.class, () -> authenticationService.register(account));
        verify(accountRepository, never()).save(any());
    }

    @Test
    void reset_password_non_admin_sends_email() {
        Account account = Account.builder()
                .email("user@example.com")
                .role("USER")
                .build();
        ReflectionTestUtils.setField(account, "id", 10L);
        when(accountRepository.findByEmail("user@example.com")).thenReturn(Optional.of(account));
        when(jwtService.generateToken(account)).thenReturn("token-xyz");

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        authenticationService.resetPassword("user@example.com");

        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
        verify(emailService).sendEmail(eq("user@example.com"), anyString(), urlCaptor.capture());
        assertNotNull(urlCaptor.getValue());
        assertTrue(urlCaptor.getValue().contains("/api/v1/auth/reset"));
    }

    @Test
    void reset_password_admin_does_nothing() {
        Account account = Account.builder()
                .email("admin@example.com")
                .role("ADMIN")
                .build();
        ReflectionTestUtils.setField(account, "id", 11L);
        when(accountRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(account));

        authenticationService.resetPassword("admin@example.com");

        verify(jwtService, never()).generateToken(any());
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void verify_valid_token_sends_new_password() {
        Account account = Account.builder()
                .email("user@example.com")
                .password("old")
                .build();
        ReflectionTestUtils.setField(account, "id", 1L);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(jwtService.isValid("token", account)).thenReturn(true);
        when(passwordEncoderService.encodedPassword("888888")).thenReturn("encoded");

        authenticationService.verify("token", 1L);

        assertEquals("encoded", account.getPassword());
        verify(emailService).sendEmail(eq("user@example.com"), eq("New Pass"), eq("888888"));
    }

    @Test
    void verify_invalid_token_does_nothing() {
        Account account = Account.builder()
                .email("user@example.com")
                .password("old")
                .build();
        ReflectionTestUtils.setField(account, "id", 2L);
        when(accountRepository.findById(2L)).thenReturn(Optional.of(account));
        when(jwtService.isValid("bad-token", account)).thenReturn(false);

        authenticationService.verify("bad-token", 2L);

        assertEquals("old", account.getPassword());
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
        verify(passwordEncoderService, never()).encodedPassword(anyString());
    }
}
