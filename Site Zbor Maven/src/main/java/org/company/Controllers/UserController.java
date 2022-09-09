package org.company.Controllers;

import org.company.DATABASE;
import org.company.Models.User;
import org.company.Models.UserRole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

public class UserController {
    public static ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String getAllUsers = "SELECT * FROM users";
        DATABASE db = DATABASE.getInstance();
        Connection connection = db.getConnection();
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
        DATABASE db = null;
        try {
            db = DATABASE.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = db.getConnection();
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


    public static Optional<User> getUserByUserIdAndEmailAndPassword(User addedUser) {
        User DBUser = null;
        DATABASE db = null;
        try {
            db = DATABASE.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = db.getConnection();
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
        return Optional.ofNullable(DBUser);
    }
    public static Optional<User> getUserByEmailAndPassword(User addedUser) {
        User DBUser = null;
        DATABASE db = null;
        try {
            db = DATABASE.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement()) {
            String email = new String();
            email = email.concat("\""+addedUser.getEmail()+"\"");
            String mail = new String();
            mail=mail.concat("\""+addedUser.getEmail()+"\"");
            String password =new String();
            password=password.concat("\""+ addedUser.getPassword()+"\"");
            String getUserByEmailAndPassword = "SELECT * FROM users WHERE users.mail="+email+" AND users.mail="+mail+" AND users.password="+password+";";
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
        return Optional.ofNullable(DBUser);
    }



    public static Optional<User> getUserById(User userToVerify) {
        User DBUser = null;
        DATABASE db = null;
        try {
            db = DATABASE.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection = db.getConnection();
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
            System.out.println("SQL ERROR");
        }
        return Optional.ofNullable(DBUser);
    }

}

