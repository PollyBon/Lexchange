package ua.nure.dao;

import ua.nure.model.Chat;

import java.util.List;

public interface ChatDao {

    void createChat(Chat chat);

    void deleteChatById(long id);

    List<Chat> findAllChatsByUserId(long userId);

    List<Chat> findAllChatsByUserId(long userId, boolean fetchUsers);

    Chat findChatById(long chatId);

    Chat findChatById(long chatId, boolean fetchUsers);
}
