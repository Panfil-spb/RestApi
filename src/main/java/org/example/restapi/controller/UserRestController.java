package org.example.restapi.controller;

import org.example.restapi.model.User;
import org.example.restapi.service.RoleService;
import org.example.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080") // Укажите свой фронтенд-домен!
@RequestMapping("api/user")
public class UserRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/auth")
    public ResponseEntity<String> getAuthUserInfo() {
        User authUser = userService.getAuthUser();
        return new ResponseEntity<>(authUser.getName() + " with  roles: " + authUser.getStringRoles(), HttpStatus.OK);
    }

    @GetMapping
    public User getUser() {
        return userService.getAuthUser();
    }
}
