package ua.nure.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.dao.AppUserDao;
import ua.nure.model.AppUser;
import ua.nure.service.AppUserService;

import java.util.List;

@Service("appUserService")
@Transactional
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserDao dao;

    public AppUser findById(long id) {
        return dao.findById(id);
    }

    public void createUser(AppUser appUser) {
        dao.createUser(appUser);
    }

    public void updateUser(AppUser appUser) {
        AppUser user = dao.findById(appUser.getId());
        if (user != null) {
            user.setBirthDate(appUser.getBirthDate());
            user.setCountry(appUser.getCountry());
            user.setEmail(appUser.getEmail());
            user.setFirstName(appUser.getFirstName());
            user.setLastName(appUser.getLastName());
            user.setNativeLanguage(appUser.getNativeLanguage());
            user.setPassword(appUser.getPassword());
            user.setUrl(appUser.getUrl());
        }
    }

    public void deleteUserByEmail(String email) {
        dao.deleteUserByEmail(email);
    }

    public List<AppUser> findAllUsers() {
        return dao.findAllUsers();
    }

    public AppUser findUserByEmail(String email) {
        return dao.findUserByEmail(email);
    }

    public boolean isEmployeeEmailUnique(Long id, String email) {
        AppUser user = findUserByEmail(email);
        return (user == null || ((id != null) && (user.getId() == id)));
    }
}