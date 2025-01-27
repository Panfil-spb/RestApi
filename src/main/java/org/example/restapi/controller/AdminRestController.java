package org.example.restapi.controller;

import org.example.restapi.model.User;
import org.example.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("admin/")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView showAdminPanel(ModelMap modelMap) {
        return new ModelAndView("admin/admin");
    }

    @GetMapping("/auth")
    public ResponseEntity<String> getAuthUserInfo() {
        User authUser = userService.getAuthUser();
        return new ResponseEntity<>(authUser.getName() + " with  roles: " + authUser.getStringRoles(), HttpStatus.OK);
    }

    @GetMapping("/get_users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get_user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add_user")
    public ResponseEntity<Void> createNewUser(@RequestBody User user) {
        userService.addNewUserFromForm(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update_user")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        userService.editUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
