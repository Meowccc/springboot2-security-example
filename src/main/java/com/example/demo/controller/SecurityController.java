package com.example.demo.controller;

import com.example.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meow
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class SecurityController {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("public")
    public String method1() {
        return "public";
    }

    @GetMapping("public/word/{plainText}/cipher")
    public String getCipherPass(@PathVariable String plainText) {
        return passwordEncoder.encode(plainText);
    }

    @GetMapping("private")
    public String method2() {
        return "private";
    }

    @GetMapping("error/forward")
    public String errorForward(@RequestHeader String errorCode) {
        throw new RuntimeException(errorCode);
    }
}
