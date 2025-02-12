package org.example.restapi.service;


import org.example.restapi.model.Role;
import org.example.restapi.model.User;
import org.example.restapi.repositories.UsersRepository;
import org.example.restapi.security.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    private final UsersRepository usersRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(RoleService roleServiceImp, PasswordEncoder passwordEncoder,
                          UsersRepository usersRepository) {
        this.roleService = roleServiceImp;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(passwordEncoder.encode("test"));
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Transactional
    @Override
    public void addNewUserFromForm(User user) {
        Set<Role> roles = user.getRoles().stream()
                .map(roleId -> roleService.getRoleById(roleId.getId()))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        addUser(user);
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        usersRepository.delete(usersRepository.getUserById(id));
    }

    @Transactional
    @Override
    public void editUser(User user) {
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return usersRepository.getUserById(id);
    }


    @Override
    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();
        return userDetailsImp.getUser();
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();

    }
}