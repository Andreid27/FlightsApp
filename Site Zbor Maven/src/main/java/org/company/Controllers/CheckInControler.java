package org.company.Controllers;

import org.company.DATABASE;
import org.company.Models.CheckIn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CheckInControler {
    public static CheckIn getRezervationByIdSeats(int reservationId, int userId) throws SQLException {
        short nrLocuri = 0;
        ArrayList<String> seats = new ArrayList<>();
        int max_airplane_seats = 0;
        DATABASE db = DATABASE.getInstance();
        Connection connection = db.getConnection();
        String getRezervationByIdSeats = "SELECT rezervare.locuri,rezervare.id_user,aeronava.nr_max_locuri\n" +
                " FROM rezervari rezervare \n" +
                " JOIN zboruri zbor\n" +
                "  ON zbor.id=rezervare.id_zbor\n" +
                "JOIN aeronave aeronava\n" +
                "    ON aeronava.id=zbor.aeronava \n" +
                "WHERE rezervare.id=" + reservationId + ";";
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
        String getUsedSeats = "SELECT loc FROM persoane_cin WHERE id_rezervare=" + reservationId + ";";
        try (ResultSet resultSet1 = statement.executeQuery(getUsedSeats)) {
            while (resultSet1.next()) {
                String seat = resultSet1.getString("loc");
                seats.add(seat);
            }
        }

        CheckIn checkIn = new CheckIn(nrLocuri,seats,max_airplane_seats);
        return checkIn;
    }
}
