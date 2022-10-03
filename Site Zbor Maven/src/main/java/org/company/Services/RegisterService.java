package org.company.Services;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.company.APImethods.POST;
import org.company.dao.UserDao;
import org.company.Models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Optional;

import static org.company.APImethods.POST.postRequest;

public class RegisterService {
    public static void register(HttpExchange exchange) throws IOException, ParseException {
        boolean successfulRegister = false;
        JSONObject jsonObject = postRequest(exchange);
        User user = new Gson().fromJson(jsonObject.toString(), User.class);
        boolean registrationCompleted = UserDao.addUser(user);
        if (registrationCompleted){
            Optional<User> user1 = UserDao.getUserByEmailAndPassword(user);
            User dbUser = user1.orElseGet(() -> new User(0,null));
            POST.postResponse(exchange,dbUser.toString(),200);
        }
        else {POST.postResponse(exchange,"This mail has already been used. ",406);}
    }



}
