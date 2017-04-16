package ua.nure.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.nure.dao.AbstractDao;
import ua.nure.dao.DictionaryDao;
import ua.nure.model.Dictionary;

import java.util.List;

@Repository("dictionaryDao")
public class DictionaryDaoImpl extends AbstractDao<Long, Dictionary> implements DictionaryDao {

    public void createDictionary(Dictionary dictionary) {
        persist(dictionary);
    }

    public List<Dictionary> findDictionariesByUserId(long userId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("user.id", userId));
        return (List<Dictionary>) criteria.list();
    }

    public Dictionary findDictionaryById(long dictionaryId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", dictionaryId));
        return (Dictionary) criteria.uniqueResult();
    }
}
