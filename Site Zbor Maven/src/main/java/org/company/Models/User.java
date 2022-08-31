package org.company.Models;

public class User {
    private String userName;
    private String email;
    private String password;
    private int userId;


    public User(String userName, String email, String password, int userId) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userId=userId;
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

    @Override
    public String toString() {
        return "{" +
                "\"userName\":\"" + userName + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"password\":\"" + password + '\"' +
                ", \"userId\":\"" + userId + '\"' +
                "}";
    }
//this.userName==user.getUserName()&&this.email==user.getEmail()&&
    public boolean verifyUser(User user){
        boolean  verified = false;
        if(this.userId==user.getUserId()&&this.userName.equals(user.getUserName())&&this.email.equals(user.getEmail())&&this.password.equals(user.getPassword()))
        {verified=true;}
        return verified;
    }


}
