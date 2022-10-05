package org.company.Services;

import org.company.Models.Flight;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class FlightService {
    public static Flight calculatePriceNow(short option, short passangersNo, Flight flight) {
        double price = flight.getPrice();
        switch (option) {
            case (0):{price = price;break;} //NO PRMOTION
            case(1):{ //PROMOTION ONLY BY DATE
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime departureDate = LocalDateTime.parse(flight.getDepartureTimestamp(), formatter);
                long period = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.from(departureDate));
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
                break;
            }
            case(2):{//PROMOTION ONLY BY PASSANGER NUMBER
                if(passangersNo<15){
                    price=price;
                }else if(passangersNo<30){
                    price=0.80*price;
                }else if(passangersNo<50){
                    price=0.90*price;
                }else if(passangersNo<100){
                    price=price;
                }else if(passangersNo>100){
                    price=1.2*price;
                }
                break;
            }

            case(3):{//PROMOTION BY PASSANGER NUMBER AND DATE
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime departureDate = LocalDateTime.parse(flight.getDepartureTimestamp(), formatter);
                long period = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.from(departureDate));
                if(period >= 365){
                    if(passangersNo<15){
                        price=0.90*price;}
                    else if(passangersNo <= 35){
                        price=0.95*price;}
                    else
                    {price=price;}
                }else if(period>182){
                    if(passangersNo<15){
                        price=0.75*price;}
                    else if(passangersNo<= 35){
                        price=0.80*price;}
                    else if(passangersNo<50)
                    {price=0.85*price;}
                    else if(passangersNo<100)
                    {price=0.90*price;}
                    else
                    {price=0.97*price;}
                }else if(period>60) {
                    if(passangersNo<15){
                        price=price;}
                    else if(passangersNo<= 35){
                        price=0.75*price;}
                    else if(passangersNo<50)
                    {price=0.80*price;}
                    else if(passangersNo<100)
                    {price=0.85*price;}
                    else
                    {price=0.97*price;}

                } else if (period>50){
                    if(passangersNo<15){
                        price=1.5*price;}
                    else if(passangersNo<= 35){
                        price=0.5*price;}
                    else if(passangersNo<50)
                    {price=0.6*price;}
                    else if(passangersNo<100)
                    {price=0.85*price;}
                    else
                    {price=0.97*price;}
                }else if (period>30){
                    if(passangersNo<15){
                        price=3*price;}
                    else if(passangersNo<= 35){
                        price=0.65*price;}
                    else if(passangersNo<50)
                    {price=0.75*price;}
                    else if(passangersNo<100)
                    {price=1*price;}
                    else
                    {price=1.1*price;}
                }else if (period>20){
                    if(passangersNo<15){
                        price=4*price;}
                    else if(passangersNo<= 35){
                        price=0.80*price;}
                    else if(passangersNo<50)
                    {price=0.95*price;}
                    else if(passangersNo<100)
                    {price=1*price;}
                    else
                    {price=1.3*price;}
                }else if (period>10){
                    if(passangersNo<15){
                        price=4.5*price;}
                    else if(passangersNo<= 35){
                        price=1.1*price;}
                    else if(passangersNo<50)
                    {price=1.1*price;}
                    else if(passangersNo<100)
                    {price=1.1*price;}
                    else
                    {price=2*price;}
                }else if (period<=10){
                    if(passangersNo<15){
                        price=7*price;}
                    else if(passangersNo<= 35){
                        price=2*price;}
                    else if(passangersNo<50)
                    {price=2.5*price;}
                    else if(passangersNo<100)
                    {price=3.5*price;}
                    else
                    {price=5*price;}
                }
                break;
            }
        }
        return new Flight(flight.getId(), flight.getFlightNumber(), flight.getCompany(), flight.getAirplane(), flight.getDepartureTimestamp(), flight.getLandingTimestamp(), flight.getDepartureLocation(),flight.getLandingLocation(), (float) price,flight.getStatus_CIN());
    }
}
