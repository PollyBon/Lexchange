package ua.nure.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.dao.ChatDao;
import ua.nure.model.Chat;
import ua.nure.service.ChatService;

import java.util.List;

@Service("appChatService")
@Transactional
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatDao dao;

    public void createChat(Chat chat) {
        dao.createChat(chat);
    }

    public void deleteChatById(long id) {
        dao.deleteChatById(id);
    }

    public List<Chat> findAllChatsByUserId(long userId) {
        return dao.findAllChatsByUserId(userId);
    }

    public List<Chat> findAllChatsByUserId(long userId, boolean fetchUsers) {
        return dao.findAllChatsByUserId(userId, fetchUsers);
    }

    public Chat findChatById(long chatId) {
        return dao.findChatById(chatId);
    }

    public void updateChatStatus(long chatId) {
        Chat chat = dao.findChatById(chatId);

        chat.setActive(false);
    }

    @Override
    public Chat findChatById(long chatId, boolean fetchUsers) {
       return dao.findChatById(chatId, fetchUsers);
    }
}
