package org.company.Models;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class CheckIn {
    private int seatsToGenerate;
    private HashSet<String> usedSeats;
    private int max_airplane_seats;
    private HashSet<String> generatedSeats = new HashSet<>();
    private short rows;
    private HashSet<Passenger> passengers;
    int reservationId;

    private Flight flight;


    public CheckIn(int seatsToGenerate, HashSet<String> usedSeats, int max_airplane_seats, HashSet<String> generatedSeats, short rows, HashSet<Passenger> passengers, int reservationId, Flight flight) {
        this.seatsToGenerate = seatsToGenerate;
        this.usedSeats = usedSeats;
        this.max_airplane_seats = max_airplane_seats;
        this.generatedSeats = generatedSeats;
        this.rows = rows;
        this.passengers = passengers;
        this.reservationId = reservationId;
        this.flight = flight;
    }

    public CheckIn(int seatsToGenerate, HashSet<String> usedSeats, int max_airplane_seats, HashSet<Passenger> passengers, int reservationId) {
        this.seatsToGenerate = seatsToGenerate;
        this.usedSeats = usedSeats;
        this.max_airplane_seats = max_airplane_seats;
        this.passengers = passengers;
        this.reservationId = reservationId;
        this.rows = (short) (max_airplane_seats/6);
    }

    public CheckIn(HashSet<Passenger> passengers, int reservationId) {
        this.passengers = passengers;
        this.reservationId = reservationId;
    }


    public CheckIn(HashSet<Passenger> passengers, Flight flight) {
        this.passengers = passengers;
        this.flight = flight;
    }

    public int getSeatsToGenerate() {
        return seatsToGenerate;
    }

    public HashSet<String> getUsedSeats() {
        return usedSeats;
    }

    public int getMax_airplane_seats() {
        return max_airplane_seats;
    }

    public short getRows() {
        return rows;
    }

    public Flight getFlight() {
        return flight;
    }

    public HashSet<String> getGeneratedSeats() {
        return generatedSeats;
    }

    public HashSet<Passenger> getPassengers() {
        return passengers;
    }

    public int getReservationId() {
        return reservationId;
    }









}
