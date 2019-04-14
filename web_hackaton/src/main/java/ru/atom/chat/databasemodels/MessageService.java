package ru.atom.chat.databasemodels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private IdbMessageDao messageDao;

    @Override
    public List<DbMessage> getAllMessages() {
        return messageDao.getAllArticles();
    }

    @Override
    public DbMessage getMessageById(int messageId) {
        DbMessage obj = messageDao.getMessageById(messageId);
        return obj;
    }

    @Override
    public boolean addMessage(DbMessage message) {
        messageDao.addMessage(message);
        return true;
    }

    @Override
    public void updateMessage(DbMessage message) {
        messageDao.updateMessage(message);
    }

    @Override
    public void deleteMessage(int messageId) {
        messageDao.deleteMessage(messageId);
    }
}
