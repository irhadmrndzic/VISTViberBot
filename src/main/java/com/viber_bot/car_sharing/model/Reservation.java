package com.viber_bot.car_sharing.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Collection;

@Entity
@Table(name = "Reservation")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="userID")
    private User user;

    @ManyToOne
    @JoinColumn(name="routeID")
    private Route route;

    @Column(name = "reservedseats")
    private int reserveadSeats;

    protected Reservation(){
    }

    public Reservation( User user, Route route, int reserveadSeats) {

        this.user = user;
        this.route = route;
        this.reserveadSeats = reserveadSeats;
    }

    public Reservation(int reservedseats) {
        this.reserveadSeats = reservedseats;
    }

    public int getId() {
        return id;
    }

    public void setId(int reservationID) {
        this.id = reservationID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getReserveadSeats() {
        return reserveadSeats;
    }



    public void setReserveadSeats(int reserveadSeats) {
        this.reserveadSeats = reserveadSeats;
    }
    @Override
    public String toString() {
        return "Reservation [User = " + user.getName() + " , route start = "
                + route.getStart() + ", route destination = " + route.getDestination()
                + "]";
    }

}
