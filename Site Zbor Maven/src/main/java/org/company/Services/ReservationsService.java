package org.company.Services;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.company.APImethods.POST;
import org.company.DATABASE;
import org.company.Models.Reservation;
import org.company.Models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.company.APImethods.POST.postRequest;

public class ReservationsService {
    public static void getReservation(HttpExchange exchange) throws IOException, ParseException, SQLException {
        boolean successfulRegister = false;
        JSONObject jsonObject = postRequest(exchange);
        User user = new Gson().fromJson(jsonObject.toString(), User.class);
        boolean userMatch = DATABASE.verifyUser(user);
        if (userMatch){
            ArrayList<Reservation> reservations = DATABASE.getUserRezervations(user);
            String rezervationsJSONString = reservations.toString();
            POST.postResponse(exchange,rezervationsJSONString,200);
        }
        else {POST.postResponse(exchange,"Unauthorized",401);}
    }
}
