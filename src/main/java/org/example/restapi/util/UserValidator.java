package org.example.restapi.util;

import org.example.restapi.model.User;
import org.example.restapi.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    public UserValidator(UserDetailsServiceImp userDetailsServiceImp) {
        this.userDetailsServiceImp = userDetailsServiceImp;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            userDetailsServiceImp.loadUserByUsername(user.getName());
        }
        catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.reject("username", new String[]{" "}, "Человек с таким именем етсь");
    }
}
