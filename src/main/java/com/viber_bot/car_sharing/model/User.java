package com.viber_bot.car_sharing.model;

import com.viber_bot.car_sharing.repository.UserRepository;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User  {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userID;

    @Column(name = "viberID")
    private String viberID;

    @Column(name = "name")
    private String name;

    @Column(name = "subscribed")
    private boolean subscribed;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();

    protected User(){

    }

    public User(String name, boolean subscribed) {
        this.name = name;
        this.subscribed = subscribed;
    }

    public String getViberID(){return this.viberID;}
    public String getName(){return this.name;}
}


