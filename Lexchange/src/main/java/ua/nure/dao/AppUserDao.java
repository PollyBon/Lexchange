package ua.nure.dao;

import ua.nure.model.AppUser;

import java.util.List;

public interface AppUserDao {

    AppUser findById(long id);

    void createUser(AppUser appUser);

    void deleteUserByEmail(String email);

    List<AppUser> findAllUsers();

    AppUser findUserByEmail(String email);
}
