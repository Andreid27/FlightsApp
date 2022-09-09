package org.company.Controllers;

import org.company.DATABASE;
import org.company.Models.Airplane;
import org.company.Models.Flight;
import org.company.Models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FlightController {


    public static ArrayList<Flight> getUpcomingFlights(User user) throws SQLException {
        ArrayList<Flight> flights = new ArrayList<>();
        int userId = user.getUserId();
        String getUserReservations = "SELECT\n" +
                "  zbor.id as zbor_id,zbor.FlightNumber,\n" +
                "  zbor.aeronava as aeronava_id,aeronava.Producator, aeronava.Model,aeronava.nr_max_locuri,\n" +
                "  zbor.Companie,zbor.departure_timestamp,zbor.landing_timestamp, zbor.departure_location,zbor.landing_location\n" +
                "FROM zboruri zbor \n" +
                "JOIN aeronave aeronava\n" +
                "    ON aeronava.id=zbor.aeronava WHERE departure_timestamp>=now()"+";";

        DATABASE db = DATABASE.getInstance();
        Connection connection = db.getConnection();
        Statement statement = connection.createStatement();
        try(ResultSet resultSet = statement.executeQuery(getUserReservations)){
            while (resultSet.next()){
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
                flights.add(new Flight(zbor_id,FlightNumber,Companie,new Airplane(aeronava_id,Producator,Model,nr_max_locuri),departure_timestamp,landing_timestamp,departure_location,landing_location));
            }
        }
        return flights;
    }


    public static Map getDestinations() throws SQLException {
        ArrayList<Flight> flights = new ArrayList<>();
        String getDepartures = "SELECT departure_location FROM zboruri \n" +
                "WHERE id IN \n" +
                "(SELECT MIN(id) FROM zboruri GROUP BY departure_location);";

        String getLandings = "SELECT landing_location FROM zboruri\n" +
                "WHERE id IN\n" +
                "(SELECT MIN(id) FROM zboruri GROUP BY landing_location);";
        String departureLocations = new String();
        String landingLocations = new String();
        DATABASE db = DATABASE.getInstance();
        Connection connection = db.getConnection();
        Statement statement = connection.createStatement();
        try(ResultSet resultSet = statement.executeQuery(getDepartures)){
            while (resultSet.next()){
                String departure_location = resultSet.getString("departure_location");
                departureLocations = departureLocations.concat("/"+departure_location);
            }
        }
        try(ResultSet resultSet = statement.executeQuery(getLandings)){
            while (resultSet.next()){
                String landing_location = resultSet.getString("landing_location");
                landingLocations = landingLocations.concat("/"+landing_location);
            }
        }
        Map<String,String> destinations = new HashMap<>();
        destinations.put("Departure Cities",departureLocations);
        destinations.put("Landing Cities",landingLocations);

        return destinations;
    }
}
