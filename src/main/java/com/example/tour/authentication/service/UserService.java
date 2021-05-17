package com.example.tour.authentication.service;

import com.example.tour.authentication.domain.UserDTO;
import com.example.tour.authentication.domain.UserRepository;
import com.example.tour.authentication.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUserByUsername(String username){

        return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }
    public void createUser(UserDTO userDTO){
        var user = new User();
        Optional<User> byUsername = userRepository.findByUsername(userDTO.getUsername());
        if (byUsername.isPresent()){
            throw new RuntimeException("User Already Exists.Please use a different Username");
        }
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setRole("CONTRIBUTOR");
        userRepository.save(user);
    }

    public void createAdmin(User user){
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername.isEmpty()) {
            userRepository.save(user);
        }

    }
}
