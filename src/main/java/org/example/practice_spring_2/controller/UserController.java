package org.example.practice_spring_2.controller;

import org.example.practice_spring_2.entity.User;
import org.example.practice_spring_2.exception.ResourceNotFoundException;
import org.example.practice_spring_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/paginated")
    public Page<User> getAllUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return userService.getAllUsersPaginated(page, size, sortBy);
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        if(user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        return user;
    }
}
