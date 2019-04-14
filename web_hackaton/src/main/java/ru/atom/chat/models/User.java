package ru.atom.chat.models;

public class User {
    public String username;
    public String password;

    public User(){}

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (((User) obj).username.equals(this.username) && ((User) obj).password.equals(this.password)) {
            return true;
        }

        return false;
    }
}
