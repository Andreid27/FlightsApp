package org.company.Services;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.company.APImethods.POST;
import org.company.DATABASE;
import org.company.Models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.company.APImethods.POST.postRequest;

public class LoginService {

    public static void login(HttpExchange exchange) throws IOException, ParseException {
        boolean successfulLogin = false;
        JSONObject jsonObject = postRequest(exchange);
        User user = new Gson().fromJson(jsonObject.toString(), User.class);
        ArrayList<User> users = null;
        try {
            users = DATABASE.getAllUsers();
        } catch (SQLException e) {
            POST.postResponse(exchange,"USER NOT FOUND",401);
        }
        for (User userDB:users){
            if((userDB.getEmail().equals(user.getEmail())) && (userDB.getPassword().equals(user.getPassword()))){
                successfulLogin = true;
                user = userDB;
            }
        }
        if (successfulLogin){
            POST.postResponse(exchange,user.toString(),200);
    }
        else {POST.postResponse(exchange,"Wrong user and/or password",401);}
}

}
