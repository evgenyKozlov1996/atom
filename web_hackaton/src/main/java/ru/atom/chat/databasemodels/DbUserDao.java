package ru.atom.chat.databasemodels;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class DbUserDao implements IdbUserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<DbUser> getUsers() {
        String hql = "FROM DbUser as usr ORDER BY usr.id";
        return (List<DbUser>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public void addUser(DbUser user) {
        entityManager.persist(user);
    }
}
