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
@RequestMapping("api/admin")
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/auth")
    public ResponseEntity<String> getAuthUserInfo() {
        User authUser = userService.getAuthUser();
        return new ResponseEntity<>(authUser.getName() + " with  roles: " + authUser.getStringRoles(), HttpStatus.OK);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(user);
        System.out.println("?????????????????????????");
        userService.addNewUserFromForm(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public void updateUser(@RequestBody User user) {
        userService.editUser(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
