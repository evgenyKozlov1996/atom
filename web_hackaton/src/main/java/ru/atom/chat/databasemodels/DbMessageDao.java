package ru.atom.chat.databasemodels;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class DbMessageDao implements IdbMessageDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DbMessage getMessageById(int id) {
        return entityManager.find(DbMessage.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DbMessage> getAllArticles() {
        String hql = "FROM messages as msg ORDER BY msg.id";
        return (List<DbMessage>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public void addMessage(DbMessage message) {
        entityManager.persist(message);
    }

    @Override
    public void updateMessage(DbMessage message) {
        DbMessage msg = getMessageById(message.getId());
        msg.setMessage(message.getMessage());
        msg.setDate(message.getDate());
        msg.setName(message.getName());
        entityManager.flush();
    }

    @Override
    public void deleteMessage(int messageId) {
        entityManager.remove(getMessageById(messageId));
    }
}
