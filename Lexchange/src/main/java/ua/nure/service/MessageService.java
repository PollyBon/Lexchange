package ua.nure.service;

import ua.nure.model.Message;

import java.util.List;

public interface MessageService {

    void createMessage(Message message);

    void deleteMessageById(long messageId);

    List<Message> findAllMessagesForChat(long chatId);
}
