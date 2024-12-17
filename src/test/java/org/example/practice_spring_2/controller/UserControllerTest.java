package org.example.practice_spring_2.controller;

import org.example.practice_spring_2.entity.User;
import org.example.practice_spring_2.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsers_returnsAllUsersSuccess() throws Exception {
        // Arrange
        User mockUser1 = new User();
        mockUser1.setId(1L);
        mockUser1.setEmail("testuser1@gmail.com");

        User mockUser2 = new User();
        mockUser2.setId(2L);
        mockUser2.setEmail("testuser2@gmail.com");

        List<User> mockUsers = Arrays.asList(mockUser1, mockUser2);

        // Mock the service method
        when(userService.getAllUsers()).thenReturn(mockUsers);

        // Act & Assert
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("testuser1@gmail.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].email").value("testuser2@gmail.com"));
    }
}
