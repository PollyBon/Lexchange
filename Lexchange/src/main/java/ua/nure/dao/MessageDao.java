package ua.nure.dao;

import ua.nure.model.Message;

import java.util.List;

public interface MessageDao {

    void createMessage(Message message);

    void deleteMessageById(long messageId);

    List<Message> findAllMessagesForChat(long chatId);
}
