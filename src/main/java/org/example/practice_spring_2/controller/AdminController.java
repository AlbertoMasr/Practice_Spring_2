package org.example.practice_spring_2.controller;

import org.example.practice_spring_2.entity.User;
import org.example.practice_spring_2.exception.ResourceNotFoundException;
import org.example.practice_spring_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public User saveUser(@RequestBody User user) {
        if(user.getId() != null){
            user.setId(null);
        }
        return userService.saveUser(user);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable long id) {
        User user = userService.getUserById(id);
        if(user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userService.deleteUser(id);
        return "User deleted successfully with id: " + id;
    }
}
