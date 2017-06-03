package ua.nure.service;

import ua.nure.model.AppUser;
import ua.nure.model.bean.SearchBean;
import ua.nure.model.enumerated.Role;

import java.util.List;
import java.util.Map;

public interface AppUserService {

    AppUser findById(long id);

    void blockById(long id);

    AppUser findById(long id, boolean fetchChats);

    List<AppUser> findByCriteria(SearchBean bean);

    Map<String, Long> countRegions();

    AppUser findByApprovementCode(String code);

    void createOrUpdate(AppUser appUser);

    void createUser(AppUser appUser);

    void updateUser(AppUser appUser);

    void updateUser(AppUser appUser, Role role);

    void deleteUserByEmail(String email);

    List<AppUser> findAllUsers();

    AppUser findUserByEmail(String email);

    boolean isEmployeeEmailUnique(Long id, String email);

    List<AppUser> findUsersOfChat(long chatId);
}
