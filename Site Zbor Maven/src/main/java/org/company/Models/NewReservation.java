package org.company.Models;

public class NewReservation {
    private int flightId;
    private int seatsNumber;

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
