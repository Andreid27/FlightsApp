package org.company.Controllers;

import org.company.DATABASE;
import org.company.Models.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReservationController {
    public static ArrayList<Reservation> getUserRezervations(User user) throws SQLException {
        ArrayList<Reservation> reservations = new ArrayList<>();
        int userId = user.getUserId();
        String getUserReservations = "SELECT rezervare.id as rezerare_id,rezervare.locuri,rezervare.pret,zbor.id as zbor_id,zbor.FlightNumber,zbor.aeronava as aeronava_id,aeronava.Producator,aeronava.Model,aeronava.nr_max_locuri,zbor.Companie,zbor.departure_timestamp,zbor.landing_timestamp, zbor.departure_location,zbor.landing_location,zbor.status_CIN FROM rezervari rezervare JOIN users u ON u.id=rezervare.id_user JOIN zboruri zbor ON zbor.id=rezervare.id_zbor JOIN aeronave aeronava ON aeronava.id=zbor.aeronava WHERE u.id="+userId+";";
        DATABASE db = DATABASE.getInstance();
        Connection connection = db.getConnection();
        Statement statement = connection.createStatement();
        try(ResultSet resultSet = statement.executeQuery(getUserReservations)){
            while (resultSet.next()){
                int rezerare_id = resultSet.getInt("rezerare_id");
                int locuri = resultSet.getInt("locuri");
                float pret = resultSet.getInt("pret");
                int zbor_id = resultSet.getInt("zbor_id");
                String FlightNumber = resultSet.getString("FlightNumber");
                int aeronava_id = resultSet.getInt("aeronava_id");
                String Producator = resultSet.getString("Producator");
                String Model = resultSet.getString("Model");
                int nr_max_locuri = resultSet.getInt("nr_max_locuri");
                String Companie = resultSet.getString("Companie");
                String departure_timestamp = resultSet.getString("departure_timestamp");
                String landing_timestamp = resultSet.getString("landing_timestamp");
                String departure_location = resultSet.getString("departure_location");
                String landing_location = resultSet.getString("landing_location");
                String status_CIN = resultSet.getString("status_CIN");
                reservations.add(new Reservation(rezerare_id,user,locuri,pret,new Flight(zbor_id,FlightNumber,Companie,new Airplane(aeronava_id,Producator,Model,nr_max_locuri),departure_timestamp,landing_timestamp,departure_location,landing_location,status_CIN)));
            }
        }
        return reservations;
    }
    public static Reservation addReservation(NewReservation newReservation, User user) throws SQLException {
        Reservation dbReservation = null;
        int userId = user.getUserId();
        Flight DBflight = FlightController.getFlightByIdAndFlightNumber(newReservation.getFlightId(),newReservation.getFlightNumber());
        DBflight.calculatePriceNow();
        double price = DBflight.getPrice();
        String transaction_number="null";
        String addReservationQuerry="INSERT INTO rezervari VALUES(null,"+userId+","+newReservation.getSeatsNumber()+","+newReservation.getFlightId()+","+price+","+transaction_number+");\n";
        String getUserReservations = "SELECT rezervare.id as rezerare_id,rezervare.locuri,rezervare.pret,zbor.id as zbor_id,zbor.FlightNumber,zbor.aeronava as aeronava_id,aeronava.Producator,aeronava.Model,aeronava.nr_max_locuri,zbor.Companie,zbor.departure_timestamp,zbor.landing_timestamp, zbor.departure_location,zbor.landing_location,zbor.status_CIN FROM rezervari rezervare JOIN users u ON u.id=rezervare.id_user JOIN zboruri zbor ON zbor.id=rezervare.id_zbor JOIN aeronave aeronava ON aeronava.id=zbor.aeronava WHERE id_user="+userId+" AND locuri="+newReservation.getSeatsNumber()+" AND id_zbor="+newReservation.getFlightId()+";";
        DATABASE db = DATABASE.getInstance();
        Connection connection = db.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(addReservationQuerry);
        try(ResultSet resultSet = statement.executeQuery(getUserReservations)){
            while (resultSet.next()){
                int rezerare_id = resultSet.getInt("rezerare_id");
                int locuri = resultSet.getInt("locuri");
                float pret = resultSet.getInt("pret");
                int zbor_id = resultSet.getInt("zbor_id");
                String FlightNumber = resultSet.getString("FlightNumber");
                int aeronava_id = resultSet.getInt("aeronava_id");
                String Producator = resultSet.getString("Producator");
                String Model = resultSet.getString("Model");
                int nr_max_locuri = resultSet.getInt("nr_max_locuri");
                String Companie = resultSet.getString("Companie");
                String departure_timestamp = resultSet.getString("departure_timestamp");
                String landing_timestamp = resultSet.getString("landing_timestamp");
                String departure_location = resultSet.getString("departure_location");
                String landing_location = resultSet.getString("landing_location");
                String status_CIN = resultSet.getString("status_CIN");
                dbReservation = new Reservation(rezerare_id,user,locuri,pret,new Flight(zbor_id,FlightNumber,Companie,new Airplane(aeronava_id,Producator,Model,nr_max_locuri),departure_timestamp,landing_timestamp,departure_location,landing_location,status_CIN));
            }
        }
        return dbReservation;
    }

}
