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
    //add(new User(user.getId(), user.getName(), true));
    public User(String id, String name, boolean sub){
        if (id!=null){
            int x = Integer.parseInt(id);
            this.userID = x;
            this.name = name;
            this.subscribed = sub;
        }
    }


    public User(int id, String name,boolean sub){

       this.userID = id;
        this.name = name;
        this.subscribed = sub;
    }


    public User(String name, boolean subscribed) {
        this.name = name;
        this.subscribed = subscribed;
    }

    public String getViberID(){return this.viberID;}
    public String getName(){return this.name;}
    @Override
    public String toString() {
        return this.name + " / " + " Subscribed: " + this.subscribed;
    }

    public int getUserID() {
        return userID;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }


    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }


}


