package org.example.restapi.service;



import org.example.restapi.model.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(Long id);

    List<Role> getAllRole();
}
