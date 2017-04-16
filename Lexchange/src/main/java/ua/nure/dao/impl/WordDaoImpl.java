package ua.nure.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.nure.dao.AbstractDao;
import ua.nure.dao.WordDao;
import ua.nure.model.Word;

import java.util.List;

@Repository("wordDao")
public class WordDaoImpl extends AbstractDao<Long, Word> implements WordDao {

    public void createWord(Word word) {
        persist(word);
    }

    public void deleteWordByValue(long dictionaryId, String word) {
        Query query = getSession().
                createSQLQuery("delete from Word where dictionaryId = :dictionaryId and value = :word");
        query.setLong("dictionaryId", dictionaryId);
        query.setString("word", word);
        query.executeUpdate();
    }

    public List<Word> findAllWords() {
        Criteria criteria = createEntityCriteria();
        return (List<Word>) criteria.list();
    }

    public Word findWordById(long wordId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", wordId));
        return (Word) criteria.uniqueResult();
    }
}
