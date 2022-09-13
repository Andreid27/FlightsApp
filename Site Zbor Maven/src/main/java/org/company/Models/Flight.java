package org.company.Models;

public class Flight {
    private int id;
    private String flightNumber;
    private String company;
    private Airplane airplane;
    private String departureTimestamp;
    private String landingTimestamp;
    private String departureLocation;
    private String landingLocation;
    private float price;
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

    public float getPrice() {
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

    public float calculatePriceNow(){
        return (float) (this.price*0.9);
    }

}
