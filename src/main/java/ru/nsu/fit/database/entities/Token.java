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
}
