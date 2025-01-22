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
@RequestMapping("")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public ModelAndView showAdminPanel(ModelMap modelMap) {
        return new ModelAndView("admin/admin");
    }

    @GetMapping("api/admin/auth")
    public ResponseEntity<String> getAuthUserInfo() {
        User authUser = userService.getAuthUser();
        return new ResponseEntity<>(authUser.getName() + " with  roles: " + authUser.getStringRoles(), HttpStatus.OK);
    }

    @GetMapping("api/admin")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("api/admin/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("api/admin")
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        userService.addNewUserFromForm(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("api/admin")
    public void updateUser(@RequestBody User user) {
        userService.editUser(user);
    }

    @DeleteMapping("api/admin/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
