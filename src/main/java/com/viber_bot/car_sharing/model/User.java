package com.viber_bot.car_sharing.model;

import com.viber_bot.car_sharing.repository.UserRepository;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userID;

    @Column(name = "viberId")
    private String viberId;

    @Column(name = "name")
    private String name;

    @Column(name = "subscribed")
    private boolean subscribed;


    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();

    public User(String viberId, boolean subscribed, String name) {
        this.viberId = viberId;
        this.name = name;
        this.subscribed = subscribed;
    }

    public String getViberID() {
        return this.viberId;
    }

    public String getName() {
        return this.name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setViberID(String viberID) {
        this.viberId = viberId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public User(String viberId, String name, boolean subscribed) {

        this.viberId = viberId;
        this.name = name;
        this.subscribed = subscribed;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public User(@NotNull int userID, String viberId, String name, boolean subscribed) {
        this.userID = userID;
        this.viberId = viberId;
        this.name = name;
        this.subscribed = subscribed;
    }
    public User() {
    }
    @Override
    public String toString() {
        return this.name + " / " + " Subscribed: " + this.subscribed;
    }
}


