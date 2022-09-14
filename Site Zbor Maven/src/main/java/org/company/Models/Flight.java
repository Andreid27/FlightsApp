package org.company.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Flight {
    private int id;
    private String flightNumber;
    private String company;
    private Airplane airplane;
    private String departureTimestamp;
    private String landingTimestamp;
    private String departureLocation;
    private String landingLocation;
    private double price;
    private String status_CIN;

    public Flight(int id, String flightNumber, String company, Airplane airplane, String departureTimestamp, String landingTimestamp, String departureLocation, String landingLocation,float price, String status_CIN) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.company = company;
        this.airplane = airplane;
        this.departureTimestamp = departureTimestamp;
        this.landingTimestamp = landingTimestamp;
        this.departureLocation = departureLocation;
        this.landingLocation = landingLocation;
        this.price = price;
        this.status_CIN = status_CIN;
    }
    public Flight(int id, String flightNumber, String company, Airplane airplane, String departureTimestamp, String landingTimestamp, String departureLocation, String landingLocation, String status_CIN) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.company = company;
        this.airplane = airplane;
        this.departureTimestamp = departureTimestamp;
        this.landingTimestamp = landingTimestamp;
        this.departureLocation = departureLocation;
        this.landingLocation = landingLocation;
        this.status_CIN = status_CIN;
    }

    public int getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getCompany() {
        return company;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public String getDepartureTimestamp() {
        return departureTimestamp;
    }

    public String getLandingTimestamp() {
        return landingTimestamp;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getLandingLocation() {
        return landingLocation;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus_CIN() {
        return status_CIN;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\": " + id +
                ",\n \"flightNumber\": \"" + flightNumber + "\"" +
                ",\n \"company\": \"" + company + "\"" +
                ", \n \"airplane\": " + airplane +
                ", \n \"departureTimestamp\": \"" + departureTimestamp + "\"" +
                ", \n \"landingTimestamp\": \"" + landingTimestamp + "\"" +
                ", \n \"departureLocation\": \"" + departureLocation + "\"" +
                ", \n \"landingLocation\": \"" + landingLocation + "\"" +
                ", \n \"price\": \"" + price + "\"" +
                ", \n \"status_CIN\": \"" + status_CIN + "\"" +
                "}";
    }

    public void calculatePriceNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime departureDate = LocalDateTime.parse(departureTimestamp, formatter);
        long period = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.from(departureDate));

        
        
        //DE ADAUGAT SA TINA CONT SI DE REZERVATION NUMBER
        if (period >= 365) {
            price = price *0.8;
        }else if (period>182) {
            price = price *0.85;
        }else if (period>91) {
            price = price *0.95;
        }else if(period>60) {
            price = price;
        } else if (period>50) { //AICI F IMPORTANT SA TINA CONT DE NR REZERVARI
            price=price*0.9;
        } else if (period>30) {
            price=price*1.1;
        } else if (period>20) {
            price=price*1.5;
        } else {
            price=price*2;
        }

    }
}
