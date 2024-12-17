package org.example.practice_spring_2.integration;

import org.example.practice_spring_2.entity.User;
import org.example.practice_spring_2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getUserById_Integration() {
        // Arrange
        User mockUser = new User();
        mockUser.setName("integrationTest");
        mockUser.setEmail("integration_test@gmail.com");
        String hashedPassword = passwordEncoder.encode("integration_test123");
        mockUser.setPassword(hashedPassword);
        mockUser.setRole("TEST");
        userRepository.save(mockUser);

        String url = "http://localhost:" + port + "/api/admin/user/"+ mockUser.getId();

        // Act
        User response = testRestTemplate.getForObject(url, User.class);

        // Assert
        assertEquals("test", response.getName());
    }
}
