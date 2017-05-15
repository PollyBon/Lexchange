package ua.nure.service;

import ua.nure.model.Chat;

import java.util.List;

public interface ChatService {

    void createChat(Chat chat);

    void deleteChatById(long id);

    void updateChat(Chat chat);

    List<Chat> findAllChatsByUserId(long userId);

    List<Chat> findAllChatsByUserId(long userId, boolean fetchUsers);

    Chat findChatById(long chatId);

    Chat findChatById(long chatId, boolean fetchUsers);

    void updateChatStatus(long chatId);

}
