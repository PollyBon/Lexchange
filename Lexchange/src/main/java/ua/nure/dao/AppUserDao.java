package ua.nure.dao;

import ua.nure.model.AppUser;
import ua.nure.model.bean.SearchBean;

import java.util.List;

public interface AppUserDao {

    AppUser findById(long id);

    List<AppUser> findByCriteria(SearchBean bean);

    AppUser findByApprovementCode(String code);

    void createUser(AppUser appUser);

    void deleteUserByEmail(String email);

    List<AppUser> findAllUsers();

    AppUser findUserByEmail(String email);
}
