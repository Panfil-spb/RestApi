package org.example.restapi.service;


import org.example.restapi.model.User;
import org.example.restapi.repositories.UsersRepository;
import org.example.restapi.security.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Autowired
    public UserDetailsServiceImp(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByName(username);

        if (user.isEmpty()) throw new UsernameNotFoundException("User not found!");

        return new UserDetailsImp(user.get());
    }
}
