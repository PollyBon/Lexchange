package ua.nure.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.nure.dao.AbstractDao;
import ua.nure.dao.ChatDao;
import ua.nure.dao.ComplainDao;
import ua.nure.model.Chat;
import ua.nure.model.Complain;

import java.util.List;

@Repository("complainDao")
public class ComplainDaoImpl extends AbstractDao<Long, Complain> implements ComplainDao {

    @Override
    public void createComplain(Complain complain) {
        persist(complain);
    }

    @Override
    public void deleteComplainByUserId(long id) {
        Query query = getSession().createQuery("delete from Complain c where c.appUser.id = :id");
        query.setLong("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Complain> findAllComplains() {
        Criteria criteria = createEntityCriteria();
        return criteria.list();
    }
}
