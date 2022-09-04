package org.company.Models;

public class User {
    private String userName;
    private String email;
    private String password;
    private int userId;
    private String userRole;


    public User(String userName, String email, String password, int userId, String userRole) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.userRole = userRole;
    }

    public User(int userId,String password) {
        this.password = password;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserRole() {
        return userRole;
    }

    @Override
    public String toString() {
        return "{" +
                "\"userName\":\"" + userName + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"password\":\"" + password + '\"' +
                ", \"userId\":\"" + userId + '\"' +
                ", \"userRole\":\"" + userRole + '\"' +
                "}";
    }

    public boolean verifyUser(User user){
        boolean  verified = false;
        if(this.userId==user.getUserId()&&this.password.equals(user.getPassword()))
        {verified=true;}
        return verified;
    }


}
