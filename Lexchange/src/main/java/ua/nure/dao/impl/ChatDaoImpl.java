package ua.nure.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.nure.dao.AbstractDao;
import ua.nure.dao.ChatDao;
import ua.nure.model.Chat;

import java.util.List;

@Repository("chatDao")
public class ChatDaoImpl extends AbstractDao<Long, Chat> implements ChatDao {

    public void createChat(Chat chat) {
        persist(chat);
    }

    public void deleteChatById(long id) {
        Query query = getSession().createSQLQuery("delete from Chat where id = :id");
        query.setLong("id", id);
        query.executeUpdate();
    }

    public List<Chat> findAllChatsByUserId(long userId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("user.id", userId));
        return (List<Chat>) criteria.list();
    }

    public Chat findChatById(long chatId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", chatId));
        return (Chat) criteria.uniqueResult();
    }
}
