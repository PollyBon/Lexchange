package ua.nure.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.nure.dao.AbstractDao;
import ua.nure.dao.AppUserDao;
import ua.nure.model.AppUser;

import java.util.List;

@Repository("appUserDao")
public class AppUserDaoImpl extends AbstractDao<Long, AppUser> implements AppUserDao {

    public AppUser findById(long id) {
        return getByKey(id);
    }

    public void createUser(AppUser appUser) {
        persist(appUser);
    }

    public void deleteUserByEmail(String email) {
        Query query = getSession().createSQLQuery("delete from AppUser where email = :email");
        query.setString("email", email);
        query.executeUpdate();
    }

    public List<AppUser> findAllUsers() {
        Criteria criteria = createEntityCriteria();
        return (List<AppUser>) criteria.list();
    }

    public AppUser findUserByEmail(String email) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("email", email));
        return (AppUser) criteria.uniqueResult();
    }
}
