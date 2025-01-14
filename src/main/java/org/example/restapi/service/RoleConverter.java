package org.example.restapi.service;

import org.example.restapi.model.Role;
import org.example.restapi.repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter implements Converter<String, Role> {

    @Autowired
    private RolesRepository roleRepository;

    @Override
    public Role convert(String source) {
        Long roleId = Long.valueOf(source);
        return roleRepository.findById(roleId).orElse(null);
    }
}