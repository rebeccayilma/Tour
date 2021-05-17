package com.example.tour.authentication.service;

import com.example.tour.authentication.domain.UserDTO;
import com.example.tour.authentication.domain.UserRepository;
import com.example.tour.authentication.model.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testUserByUsername() {
        User user = stubUser();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        User user1 = userService.getUserByUsername(user.getUsername());
        assertThat(user1.getUsername()).isEqualTo(user.getUsername());
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    public void testCreateUser(){
        UserDTO userDTO = stubUserDTO();
        User user = stubUser();
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.ofNullable(null));
        userService.createUser(userDTO);
        verify(userRepository, times(1)).findByUsername(user.getUsername());

    }

    public UserDTO stubUserDTO() {
        UserDTO userDTO = new UserDTO("prossie", "secret");
        return userDTO;
    }

    public User stubUser() {
        User user = new User();
        user.setUsername("prossie");
        user.setPassword("secret");
        user.setRole("ADMIN");
        return user;
    }



}
