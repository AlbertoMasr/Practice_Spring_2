package org.example.practice_spring_2.controller;

import org.example.practice_spring_2.entity.User;
import org.example.practice_spring_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from public API controller";
    }

    @PostMapping("/init-admin")
    public ResponseEntity<String> createAdmin(@RequestBody User user) {
        if (userService.hasUser()) {
            return ResponseEntity.badRequest().body("Admin already exists! Cannot create another initial admin.");
        }

        user.setRole("ADMIN");
        userService.saveUser(user);
        return ResponseEntity.ok("Initial admin user created successfully!");
    }
}
