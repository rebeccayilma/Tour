package com.example.tour.authentication;

import com.example.tour.authentication.domain.UserDTO;
import com.example.tour.authentication.model.User;
import com.example.tour.authentication.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("tourAdmin");
        user.setPassword("tourPass");
        user.setRole("ADMIN");
    }
}
