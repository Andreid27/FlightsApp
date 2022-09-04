package org.company;

import org.company.Models.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DATABASE {
    public static Connection startDBconnection() throws SQLException {
        //DB parameters
        final String jdbUrl = "jdbc:mysql://localhost:33061/FlightsApp";
        final String USER = "root";
        final String PASSWORD = "";
        Connection connection = DriverManager.getConnection(jdbUrl, USER, PASSWORD);
        return connection;
    }
    public static ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String getAllUsers = "SELECT * FROM users";
        Connection connection = startDBconnection();
        Statement statement = connection.createStatement();
        try(ResultSet resultSet = statement.executeQuery(getAllUsers)){
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("nume");
                String email = resultSet.getString("mail");
                String password = resultSet.getString("password");
                String userRole = resultSet.getString("userRole");
                users.add(new User(userName,email,password,id, userRole));
            }
        }
        return users;
    }

    public static boolean addUser(User newUser) {
        boolean registrationComplete = false;
        try (Connection connection = startDBconnection();
                Statement statement = connection.createStatement()) {
                String userName = new String();
                userName = userName.concat("\""+newUser.getUserName()+"\"");
                String mail = new String();
                mail=mail.concat("\""+newUser.getEmail()+"\"");
                String password =new String();
                password=password.concat("\""+ newUser.getPassword()+"\"");
                String userRole =new String();
                if(newUser.getUserRole() != null && UserRole.UserRoleIsValid(newUser.getUserRole()) ) { //security issues
                    userRole=userRole.concat("\""+newUser.getUserRole()+"\"");
                } else{userRole=userRole.concat("\""+"user"+"\"");}
                String insertUsers = "INSERT INTO Users VALUES (null,"+userName+","+mail+","+password+","+userRole+");";
                statement.execute(insertUsers);
                registrationComplete = true;
            } catch (SQLException e) {
                registrationComplete = false;
        }
        return registrationComplete;
    }

    public static User getUserByEmailAndPassword(User addedUser) {
        User DBUser = null;
        try (Connection connection = startDBconnection();
             Statement statement = connection.createStatement()) {
            String userName = new String();
            userName = userName.concat("\""+addedUser.getUserName()+"\"");
            String mail = new String();
            mail=mail.concat("\""+addedUser.getEmail()+"\"");
            String password =new String();
            password=password.concat("\""+ addedUser.getPassword()+"\"");
            String getUserByEmailAndPassword = "SELECT * FROM users WHERE users.nume="+userName+" AND users.mail="+mail+" AND users.password="+password+";";
            try(ResultSet resultSet = statement.executeQuery(getUserByEmailAndPassword)){
                while (resultSet.next()){
                    int DBid = resultSet.getInt("id");
                    String DBuserName = resultSet.getString("nume");
                    String DBemail = resultSet.getString("mail");
                    String DBpassword = resultSet.getString("password");
                    String userRole = resultSet.getString("userRole");
                    DBUser = new User(DBuserName,DBemail,DBpassword,DBid, userRole);
                }
            }
        } catch (SQLException e) {
        }
        return DBUser;
    }



    public static User getUserById(User userToVerify) {
        User DBUser = null;
        try (Connection connection = startDBconnection();
             Statement statement = connection.createStatement()) {
            int userId = userToVerify.getUserId();
            String getbyId = "SELECT * FROM users WHERE users.id="+userId+";";
            try(ResultSet resultSet = statement.executeQuery(getbyId)){
                while (resultSet.next()){
                    int DBid = resultSet.getInt("id");
                    String DBuserName = resultSet.getString("nume");
                    String DBemail = resultSet.getString("mail");
                    String DBpassword = resultSet.getString("password");
                    String userRole = resultSet.getString("userRole");
                    DBUser = new User(DBuserName,DBemail,DBpassword,DBid, userRole);
                }
            }
        } catch (SQLException e) {
        }
        return DBUser;
    }

    public static ArrayList<Reservation> getUserRezervations(User user) throws SQLException {
        ArrayList<Reservation> reservations = new ArrayList<>();
        int userId = user.getUserId();
        String getUserReservations = "SELECT rezervare.id as rezerare_id,rezervare.lucuri,zbor.id as zbor_id,zbor.FlightNumber,zbor.aeronava as aeronava_id,aeronava.Producator,aeronava.Model,aeronava.nr_max_locuri,zbor.Companie,zbor.departure_timestamp,zbor.landing_timestamp, zbor.departure_location,zbor.landing_location FROM rezervari rezervare JOIN users u ON u.id=rezervare.id_user JOIN zboruri zbor ON zbor.id=rezervare.id_zbor JOIN aeronave aeronava ON aeronava.id=zbor.aeronava WHERE u.id="+userId+";";
        Connection connection = startDBconnection();
        Statement statement = connection.createStatement();
        try(ResultSet resultSet = statement.executeQuery(getUserReservations)){
            while (resultSet.next()){
                int rezerare_id = resultSet.getInt("rezerare_id");
                String lucuri = resultSet.getString("lucuri");
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
                reservations.add(new Reservation(rezerare_id,user,lucuri,new Flight(zbor_id,FlightNumber,Companie,new Airplane(aeronava_id,Producator,Model,nr_max_locuri),departure_timestamp,landing_timestamp,departure_location,landing_location)));
            }
        }
        return reservations;
    }
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
        Connection connection = startDBconnection();
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




}
