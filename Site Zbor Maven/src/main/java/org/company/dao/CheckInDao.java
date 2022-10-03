package org.company.dao;

import org.company.DATABASE;
import org.company.Models.Airplane;
import org.company.Models.CheckIn;
import org.company.Models.Flight;
import org.company.Models.Passenger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class CheckInDao {
    public static CheckIn getRezervationByIdSeats(CheckIn checkIn, int userId) throws SQLException {
        short nrLocuri = 0;
        HashSet<String> seats = new HashSet<>();
        int max_airplane_seats = 0;
        DATABASE db = DATABASE.getInstance();
        Connection connection = db.getConnection();
        String getRezervationByIdSeats = "SELECT rezervare.locuri,rezervare.id_user,aeronava.nr_max_locuri\n" +
                " FROM rezervari rezervare \n" +
                " JOIN zboruri zbor\n" +
                "  ON zbor.id=rezervare.id_zbor\n" +
                "JOIN aeronave aeronava\n" +
                "    ON aeronava.id=zbor.aeronava \n" +
                "WHERE rezervare.id=" + checkIn.getReservationId() + ";";
        Statement statement = connection.createStatement();
        try (ResultSet resultSet = statement.executeQuery(getRezervationByIdSeats)) {
            while (resultSet.next()) {
                nrLocuri = resultSet.getShort("locuri");
                int id_user = resultSet.getInt("id_user");
                max_airplane_seats = resultSet.getInt("nr_max_locuri");
                if (userId != id_user) {
                    nrLocuri = 0;
                }
            }
        }
        String getUsedSeats = "SELECT loc FROM persoane_cin WHERE id_rezervare=" + checkIn.getReservationId() + ";";
        try (ResultSet resultSet1 = statement.executeQuery(getUsedSeats)) {
            while (resultSet1.next()) {
                String seat = resultSet1.getString("loc");
                seats.add(seat);
            }
        }
        return new CheckIn(nrLocuri,seats,max_airplane_seats,checkIn.getPassengers(),checkIn.getReservationId());
    }



    public static boolean addCinDB (CheckIn checkIn) throws SQLException {
        String addCheckInQuerry="INSERT INTO persoane_CIN VALUES";
        Set<Passenger> passengers = checkIn.getPassengers();
        Set<String> seats = checkIn.getGeneratedSeats();
        String[] seatsArray = seats.toArray(new String[seats.size()]);
        short i = 0;
        for(Passenger passenger: passengers){
            addCheckInQuerry = addCheckInQuerry.concat("\n(null,\""+passenger.getSurname()+"\",\""+passenger.getName()+"\",\""+seatsArray[i]+"\",\""+checkIn.getReservationId()+"\",\""+passenger.getIdentificationNumber()+"\"),");
            i++;
        }
        addCheckInQuerry = addCheckInQuerry.substring(0, addCheckInQuerry.length() - 1) + ";";
        DATABASE db = DATABASE.getInstance();
        Connection connection = db.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(addCheckInQuerry);
        return true;
    }


//DE LUAT DATE DESPRE CHECH IN
    public static CheckIn getCheckIn (String rezervationId) throws SQLException {
            short nrLocuri = 0;
            Flight flight = null;
            HashSet<Passenger> passengers = new HashSet<>();
            DATABASE db = DATABASE.getInstance();
            Connection connection = db.getConnection();
            String getRezervationByIdSeats =
                    "SELECT rezervare.locuri,\n" +
                            "zbor.companie,zbor.departure_location,zbor.landing_location,zbor.departure_timestamp,zbor.landing_timestamp,zbor.FlightNumber,\n" +
                            "aeronava.Producator,aeronava.model\n" +
                            " FROM rezervari rezervare \n" +
                            " JOIN zboruri zbor\n" +
                            "  ON zbor.id=rezervare.id_zbor\n" +
                            "JOIN aeronave aeronava\n" +
                            "    ON aeronava.id=zbor.aeronava \n" +
                            "WHERE rezervare.id="+rezervationId+";\n";
            Statement statement = connection.createStatement();
            try (ResultSet resultSet = statement.executeQuery(getRezervationByIdSeats)) {
                while (resultSet.next()) {
                    nrLocuri = resultSet.getShort("locuri");
                    String FlightNumber = resultSet.getString("FlightNumber");
                    String Producator = resultSet.getString("Producator");
                    String Model = resultSet.getString("Model");
                    String Companie = resultSet.getString("Companie");
                    String departure_timestamp = resultSet.getString("departure_timestamp");
                    String landing_timestamp = resultSet.getString("landing_timestamp");
                    String departure_location = resultSet.getString("departure_location");
                    String landing_location = resultSet.getString("landing_location");
                    flight = new Flight(FlightNumber,Companie,new Airplane(Producator,Model),departure_timestamp,landing_timestamp,departure_location,landing_location);
                }
            }

            String getUsedSeats = "SELECT nume,prenume,identification_number,loc FROM persoane_cin WHERE id_rezervare=" +rezervationId + ";";
            try (ResultSet resultSet1 = statement.executeQuery(getUsedSeats)) {
                while (resultSet1.next()) {
                    String nume = resultSet1.getString("nume");
                    String prenume = resultSet1.getString("prenume");
                    String seat = resultSet1.getString("loc");
                    String ident_number = resultSet1.getString("identification_number");
                    passengers.add(new Passenger(prenume,nume,seat,ident_number));
                }
            }
            return new CheckIn(passengers,flight);

    }
}
