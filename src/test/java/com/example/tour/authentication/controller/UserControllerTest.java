package com.example.tour.authentication.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.example.tour.activity.ActivityController;
import com.example.tour.authentication.domain.UserDTO;
import com.example.tour.authentication.model.User;
import com.example.tour.authentication.service.UserService;
import com.example.tour.place.PlaceController;
import com.example.tour.place.PlaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Test
    public void testCreateUser() {
        UserDTO userDTO = stubUserDTO();
        UserService userService1 = mock(UserService.class);
        doNothing().when(userService1).createUser(userDTO);
        userService1.createUser(userDTO);
        verify(userService1, times(1)).createUser(userDTO);

        ResponseEntity<User> responseEntity = userController.create(userDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    public UserDTO stubUserDTO() {
        UserDTO userDTO = new UserDTO("prossie", "secret");
        return userDTO;
    }

}