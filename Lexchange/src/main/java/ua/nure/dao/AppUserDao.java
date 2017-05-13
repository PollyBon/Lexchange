package ua.nure.dao;

import org.hibernate.Session;
import ua.nure.model.AppUser;
import ua.nure.model.bean.SearchBean;

import java.util.List;
import java.util.Map;

public interface AppUserDao {

    AppUser findById(long id);

    AppUser findById(long id, boolean fetchChats);

    List<AppUser> findByCriteria(SearchBean bean);

    Map<String, Long> countRegions();

    AppUser findByApprovementCode(String code);

    void createUser(AppUser appUser);

    void deleteUserByEmail(String email);

    List<AppUser> findAllUsers();

    AppUser findUserByEmail(String email);

    Session getSession();
}
