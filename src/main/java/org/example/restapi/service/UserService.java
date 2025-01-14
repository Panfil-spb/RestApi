package org.example.restapi.service;




import org.example.restapi.model.User;

import java.util.List;

public interface UserService {
    void addNewUserFromForm(User user);

    User getAuthUser();

    void addUser(User user);

    void deleteUser(Long id);

    void editUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();
}
