package org.company.Services;

import com.sun.net.httpserver.HttpExchange;
import org.company.APImethods.GET;
import org.company.APImethods.POST;
import org.company.DATABASE;
import org.company.Models.Reservation;
import org.company.Models.User;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ReservationsService {
    public static void getReservation(HttpExchange exchange) throws IOException, ParseException, SQLException {
        boolean successfulRegister = false;
//        JSONObject jsonObject = postRequest(exchange);
//        User user = new Gson().fromJson(jsonObject.toString(), User.class);
        Map<String,String> queryMap = GET.getRequest(exchange);
        System.out.println(queryMap);
        User user = new User(Integer.parseInt(queryMap.get("userId")), queryMap.get("password"));
        User dbUser = DATABASE.getUserById(user);
        boolean userMatch = user.verifyUser(dbUser);
        if (userMatch){
            ArrayList<Reservation> reservations = DATABASE.getUserRezervations(dbUser);
            String rezervationsJSONString = reservations.toString();
            POST.postResponse(exchange,rezervationsJSONString,200);
        }
        else {POST.postResponse(exchange,"Unauthorized",401);}
    }

}
