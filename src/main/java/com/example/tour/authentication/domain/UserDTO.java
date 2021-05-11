package com.example.tour.authentication.domain;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private  String role;
}


