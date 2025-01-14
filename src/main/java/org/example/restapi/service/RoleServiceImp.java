package org.example.restapi.service;

import org.example.restapi.model.Role;
import org.example.restapi.repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private final RolesRepository rolesRepository;

    @Autowired
    public RoleServiceImp(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }


    @Override
    public Role getRoleById(Long id) {
        return rolesRepository.findById(id).get();
    }

    @Override
    public List<Role> getAllRole() {
        return rolesRepository.findAll();
    }
}
