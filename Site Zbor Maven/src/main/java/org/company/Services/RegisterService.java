package org.company.Services;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.company.APImethods.POST;
import org.company.DATABASE;
import org.company.Models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static org.company.APImethods.POST.postRequest;

public class RegisterService {
    public static void register(HttpExchange exchange) throws IOException, ParseException {
        boolean successfulRegister = false;
        JSONObject jsonObject = postRequest(exchange);
        User user = new Gson().fromJson(jsonObject.toString(), User.class);
        boolean registrationCompleted = DATABASE.addUser(user);
        if (registrationCompleted){
            user = DATABASE.getUserByEmailAndPassword(user);
            POST.postResponse(exchange,user.toString(),200);
        }
        else {POST.postResponse(exchange,"This mail has already been used. ",406);}
    }



}
