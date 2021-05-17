package com.example.tour.authentication;

import com.example.tour.authentication.domain.UserDTO;
import com.example.tour.authentication.domain.UserRepository;
import com.example.tour.authentication.model.User;
import com.example.tour.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Igniter implements CommandLineRunner {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        userService.createAdmin( new User("admin",bCryptPasswordEncoder.encode("pass"),"ADMIN"));
    }
}
