package ru.atom.chat.DatabaseModels;

import javax.persistence.*;

@Entity
@Table(name="messages")
public class DBMessage {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="date")
    private String date;

    @Column(name="message")
    private String message;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return this.username;
    }

    public void setName(String name){
        this.username=name;
    }

    public String getDate(){
        return this.date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
