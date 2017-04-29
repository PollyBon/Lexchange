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
        return findAllChatsByUserId(userId, false);
    }

    public List<Chat> findAllChatsByUserId(long userId, boolean fetchUsers) {
        Criteria criteria = createEntityCriteria();
        criteria.createAlias("users", "u");
        criteria.add(Restrictions.eq("u.id", userId));
        List<Chat> chats = (List<Chat>) criteria.list();
        if (fetchUsers) {
            chats.forEach(c -> c.getUsers().size());
        }
        return chats;
    }

    public Chat findChatById(long chatId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id", chatId));
        return (Chat) criteria.uniqueResult();
    }
}
