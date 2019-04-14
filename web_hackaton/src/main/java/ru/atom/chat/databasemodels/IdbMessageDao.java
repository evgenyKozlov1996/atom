package ru.atom.chat.databasemodels;

import java.util.List;

public interface IdbMessageDao {
    DbMessage getMessageById(int id);

    List<DbMessage> getAllArticles();

    void addMessage(DbMessage message);

    void updateMessage(DbMessage message);

    void deleteMessage(int messageId);
}
