package ua.nure.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.dao.MessageDao;
import ua.nure.model.Message;
import ua.nure.service.MessageService;

import java.util.List;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao dao;

    public void createMessage(Message message) {
        dao.createMessage(message);
    }

    public void deleteMessageById(long messageId) {
        dao.deleteMessageById(messageId);
    }

    public List<Message> findAllMessagesForChat(long chatId) {
        return dao.findAllMessagesForChat(chatId);
    }
}
