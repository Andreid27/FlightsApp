package org.company.Models;

public class NewReservation {
    private int flightId;
    private String flightNumber;
    private int seatsNumber;

    public NewReservation(int flightId, String flightNumber, int seatsNumber) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.seatsNumber = seatsNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public NewReservation(int flightId, int seatsNumber) {
        this.flightId = flightId;
        this.seatsNumber = seatsNumber;
    }


    public int getFlightId() {
        return flightId;
    }


    public int getSeatsNumber() {
        return seatsNumber;
    }

    @Override
    public String toString() {
        return "NewReservation{" +
                "flightId=" + flightId +
                ", seatsNumber=" + seatsNumber +
                '}';
    }
}
