package org.company.Models;

public class Passenger {
    int id;
    String name;
    String surname;
    String seatNumber;


    public Passenger(int id, String surname, String name,  String seatNumber) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.seatNumber = seatNumber;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
