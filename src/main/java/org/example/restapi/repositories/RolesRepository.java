package org.example.restapi.repositories;

import org.example.restapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);

    Optional<Role> findById(Long roleId);
}
