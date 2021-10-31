package com.bookstore.domain.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean isPasswordMatching(String providedPassword, String storedPassword) {
        return passwordEncoder.matches(providedPassword, storedPassword);
    }
}
