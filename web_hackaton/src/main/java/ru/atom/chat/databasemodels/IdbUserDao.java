package ru.atom.chat.databasemodels;

import java.util.List;

public interface IdbUserDao {
    List<DbUser> getUsers();

    void addUser(DbUser user);
}
