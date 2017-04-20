package ua.nure.service;

import ua.nure.model.Chat;

import java.util.List;

public interface ChatService {

    void createChat(Chat chat);

    void deleteChatById(long id);

    List<Chat> findAllChatsByUserId(long userId);

    Chat findChatById(long chatId);

    void updateChatStatus(long chatId);
}
