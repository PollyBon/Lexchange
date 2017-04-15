package ua.nure.service;

import ua.nure.model.AppUser;

import java.util.List;

public interface AppUserService {

    AppUser findById(long id);

    void createUser(AppUser appUser);

    void updateUser(AppUser appUser);

    void deleteUserByEmail(String email);

    List<AppUser> findAllUsers();

    AppUser findUserByEmail(String email);

    boolean isEmployeeEmailUnique(Long id, String email);
}
