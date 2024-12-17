package org.example.practice_spring_2.service;

import org.example.practice_spring_2.entity.User;
import org.example.practice_spring_2.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void getAllUsers_returnsAllUsers() {
        // Arrange
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
    }

    @Test
    void getAllUsersPaginated_returnsPaginatedUsers() {
        // Arrange
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);
        Page<User> page = new PageImpl<>(users);
        Pageable pageable = PageRequest.of(0, 2, Sort.by("name"));
        when(userRepository.findAll(pageable)).thenReturn(page);

        // Act
        Page<User> result = userService.getAllUsersPaginated(0, 2, "name");

        // Assert
        assertEquals(2, result.getTotalElements());
        assertTrue(result.getContent().contains(user1));
        assertTrue(result.getContent().contains(user2));
    }

    @Test
    void getUserById_existingId_returnsUser() {
        // Arrange
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void getUserById_nonExistingId_returnsNull() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    void getUserByEmail_existingEmail_returnsUser() {
        // Arrange
        User user = new User();
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserByEmail("test@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void getUserByEmail_nonExistingEmail_returnsEmpty() {
        // Arrange
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.getUserByEmail("test@example.com");

        // Assert
        assertFalse(result.isPresent());
    }
}
