package ua.nure.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.nure.dao.AbstractDao;
import ua.nure.dao.AppUserDao;
import ua.nure.model.AppUser;
import ua.nure.model.bean.SearchBean;
import ua.nure.model.enumerated.Country;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("appUserDao")
public class AppUserDaoImpl extends AbstractDao<Long, AppUser> implements AppUserDao {

    public AppUser findById(long id) {
        return getByKey(id);
    }

    public AppUser findById(long id, boolean fetchChats) {
        AppUser user = getByKey(id);
        user.getChats().size();
        return user;
    }

    public List<AppUser> findByCriteria(SearchBean bean) {
        Criteria criteria = createEntityCriteria();
        return (List<AppUser>) bean.buildCriteria(criteria).list();
    }

    public Map<String, Long> countRegions() {
        Map <String, Long> map = new HashMap<>();
        for (Country country : Country.values()) {
            Criteria criteria = createEntityCriteria();
            criteria.add(Restrictions.eq("country", country.getCode()));
            criteria.setProjection(Projections.rowCount());
            map.put (country.getCode(), (Long) criteria.uniqueResult());
        }
        return map;
    }

    public AppUser findByApprovementCode(String code) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("approvementCode", code));
        return (AppUser) criteria.uniqueResult();
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

    @Override
    public List<AppUser> findUsersOfChat(long chatId) {
        Criteria criteria = createEntityCriteria();
        criteria.createAlias("chats", "c");
        criteria.add(Restrictions.eq("c.id", chatId));
        return (List<AppUser>) criteria.list();
    }
}
