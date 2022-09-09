package org.company.Services;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.company.APImethods.POST;
import org.company.Controllers.UserController;
import org.company.Models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Optional;

import static org.company.APImethods.POST.postRequest;

public class LoginService {

    public static void login(HttpExchange exchange) throws IOException, ParseException {
        JSONObject jsonObject = postRequest(exchange);
        User user = new Gson().fromJson(jsonObject.toString(), User.class);
        Optional<User> user1 = UserController.getUserByEmailAndPassword(user);
        User dbUser = user1.orElseGet(() -> new User(0,null));
        boolean userMatch = user.verifyEmailAndPassword(dbUser);
        if (userMatch){
            POST.postResponse(exchange,dbUser.toString(),200);
    }
        else {POST.postResponse(exchange,"Wrong user and/or password",401);}
}

}
