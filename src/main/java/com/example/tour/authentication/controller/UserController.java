package com.example.tour.authentication.controller;

import com.example.tour.authentication.domain.UserDTO;
import com.example.tour.authentication.model.User;
import com.example.tour.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    @PostMapping("/new")
    public ResponseEntity<User> create(@RequestBody UserDTO user){
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }
}