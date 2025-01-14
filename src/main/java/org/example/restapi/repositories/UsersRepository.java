package org.example.restapi.repositories;

import org.example.restapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByName(String name);
    User getUserById (Long id);

    List<User> findAll();
}
