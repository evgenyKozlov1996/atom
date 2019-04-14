package ru.atom.chat.databasemodels;

import java.util.List;

public interface IMessageService {
    List<DbMessage> getAllMessages();
    DbMessage getMessageById(int messageId);
    boolean addMessage(DbMessage message);
    void updateMessage(DbMessage message);
    void deleteMessage(int messageId);
}
