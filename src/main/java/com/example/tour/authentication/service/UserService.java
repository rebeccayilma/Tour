package com.example.tour.authentication.service;

import com.example.tour.authentication.domain.UserRepository;
import com.example.tour.authentication.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }
}
