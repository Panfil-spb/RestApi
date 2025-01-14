package org.example.restapi.service;

import org.example.restapi.model.User;
import org.example.restapi.repositories.RolesRepository;
import org.example.restapi.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;
    @Autowired
    public RegistrationService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
    }

    @Transactional
    public void register(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.addRole(rolesRepository.findByName("ROLE_USER").get());
        usersRepository.save(user);
    }
}
