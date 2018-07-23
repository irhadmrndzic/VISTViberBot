package com.viber_bot.car_sharing.model;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Route")
public class Route {
    private static final long serialVersionUID = -3009157732242241606L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "start")
    private String start;

    @Column(name = "destination")
    private String destination;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "avaliableseats")
    private int avaliableseats;

    @OneToMany(mappedBy = "route")
    private List<Reservation> reservations = new ArrayList<>();

    protected Route() {
    }

    public Route(String start, String destination, LocalDate date, LocalTime time, int avaliableseats) {
        this.start = start;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.avaliableseats = avaliableseats;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getStart() { return start; }

    public void setStart(String start) { this.start = start; }

    public String getDestination() { return destination; }

    public void setDestination(String destination) { this.destination = destination; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }

    public void setTime(LocalTime time) { this.time = time; }

    public void setAvaliableSeats(int avalibleseats) { this.avaliableseats = avaliableseats; }

    public List<Reservation> getReservations() { return reservations; }

    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    public int getAvaliableseats() {
        return avaliableseats;
    }

    public void setAvaliableseats(int avaliableseats) {
        this.avaliableseats = avaliableseats;
    }
}

