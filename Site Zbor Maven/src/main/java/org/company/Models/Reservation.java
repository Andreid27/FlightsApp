package org.company.Models;

import java.util.HashSet;

public class Reservation {
    private int id;
    private User user;
    private int seats;
    private Flight flight;
    private float price;
    private HashSet<Passenger> passengers;
    private short persoaneCinNo;


    public Reservation(int id, User user, int seats,short persoaneCinNo,float price, Flight flight) {
        this.id = id;
        this.user = user;
        this.seats = seats;
        this.flight = flight;
        this.price = price;
        this.persoaneCinNo = persoaneCinNo;
    }

    public Reservation(int id, User user, int seats,float price, Flight flight) {
        this.id = id;
        this.user = user;
        this.seats = seats;
        this.flight = flight;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public int getSeats() {
        return seats;
    }

    public Flight getFlight() {
        return flight;
    }


    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\":" + id +
                ",\n \"user\": " + user +
                ",\n \"seats\": \"" + seats + "\"" +
                ",\n \"persoaneCinNo\": \"" + persoaneCinNo + "\"" +
                ",\n \"price\": \"" + price + "\"" +
                ",\n \"flight\": " + flight +
                "}";
    }
}
