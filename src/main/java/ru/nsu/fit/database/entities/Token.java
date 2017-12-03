package ru.nsu.fit.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "TOKENS")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String data;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Token(String data, User owner) {
        this.data = data;
        this.owner = owner;
    }

    public Token() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
