package ru.atom.chat.models;

public class User {
    public String username;
    public String password;

    @Override
    public boolean equals(Object obj) {
        if(((User)obj).username == this.username && ((User)obj).password == this.password)
        {
            return true;
        }

        return false;
    }
}
