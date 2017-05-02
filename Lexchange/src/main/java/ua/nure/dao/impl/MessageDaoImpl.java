package ua.nure.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.nure.dao.AbstractDao;
import ua.nure.dao.MessageDao;
import ua.nure.model.Message;

import java.util.List;

@Repository("messageDao")
public class MessageDaoImpl extends AbstractDao<Long, Message> implements MessageDao {

    public void createMessage(Message message) {
        persist(message);
    }

    public void deleteMessageById(long messageId) {
        Query query = getSession().
                createSQLQuery("delete from Message where id = :id");
        query.setLong("id", messageId);
        query.executeUpdate();
    }

    public List<Message> findAllMessagesForChat(long chatId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("chat.id", chatId));
        criteria.addOrder(Order.asc("id"));
        return (List<Message>) criteria.list();
    }
}
