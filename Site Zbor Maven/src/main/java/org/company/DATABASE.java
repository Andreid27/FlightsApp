package org.company;

import org.company.Models.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DATABASE {
    private  static DATABASE ConexiuneDB;
    private Connection connection;
    final String jdbUrl = "jdbc:mysql://localhost:33061/FlightsApp";
    final String USER = "root";
    final String PASSWORD = "";

    private DATABASE() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(jdbUrl, USER, PASSWORD);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }

    }

    public Connection getConnection() {
        return connection;
    }


    public static DATABASE getInstance() throws SQLException {
        if(ConexiuneDB == null){
            ConexiuneDB = new DATABASE();
        }
        else if (ConexiuneDB.getConnection().isClosed()) {
            ConexiuneDB = new DATABASE();
    }
        return ConexiuneDB;
    }



}
