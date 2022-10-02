package org.company.Models;

public class Passenger {
    int id;
    String name;
    String surname;
    String seatNumber;
    String identificationNumber;


    public Passenger(int id, String name, String surname, String seatNumber, String identificationNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.seatNumber = seatNumber;
        this.identificationNumber = identificationNumber;
    }

    public Passenger(String name, String surname, String seatNumber, String identificationNumber) {
        this.name = name;
        this.surname = surname;
        this.seatNumber = seatNumber;
        this.identificationNumber = identificationNumber;
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

    public String getIdentificationNumber() {
        return identificationNumber;
    }
}
