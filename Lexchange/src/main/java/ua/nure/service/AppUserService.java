package ua.nure.service;

import ua.nure.model.AppUser;
import ua.nure.model.bean.SearchBean;

import java.util.List;

public interface AppUserService {

    AppUser findById(long id);

    AppUser findById(long id, boolean fetchChats);

    List<AppUser> findByCriteria(SearchBean bean);

    AppUser findByApprovementCode(String code);

    void createOrUpdate(AppUser appUser);

    void createUser(AppUser appUser);

    void updateUser(AppUser appUser);

    void deleteUserByEmail(String email);

    List<AppUser> findAllUsers();

    AppUser findUserByEmail(String email);

    boolean isEmployeeEmailUnique(Long id, String email);
}
