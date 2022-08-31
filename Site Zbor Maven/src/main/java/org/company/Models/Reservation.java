package org.company.Models;

public class Reservation {
    private int id;
    private User user;
    private String seats;
    private Flight flight;

    public Reservation(int id, User user, String seats, Flight flight) {
        this.id = id;
        this.user = user;
        this.seats = seats;
        this.flight = flight;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getSeats() {
        return seats;
    }

    public Flight getFlight() {
        return flight;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\":" + id +
                ",\n \"user\": " + user +
                ",\n \"seats\": \"" + seats + "\"" +
                ",\n \"flight\": " + flight +
                "}";
    }
}
