package org.company.Services;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.company.APImethods.GET;
import org.company.APImethods.POST;
import org.company.DATABASE;
import org.company.Models.NewReservation;
import org.company.Models.Reservation;
import org.company.Models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import static org.company.APImethods.POST.postRequest;

public class ReservationsService {
    public static void getReservation(HttpExchange exchange) throws IOException, ParseException {
        Map<String,String> queryMap = GET.getRequest(exchange);
        User user = new User(Integer.parseInt(queryMap.get("userId")), queryMap.get("password"));
        User dbUser = DATABASE.getUserById(user);
        boolean userMatch = user.verifyUser(dbUser);
        if (userMatch){
            ArrayList<Reservation> reservations = null;
            try {
                reservations = DATABASE.getUserRezervations(dbUser);
            } catch (SQLException e) {
                POST.postResponse(exchange,"USER NOT FOUND",200);
            }
            String rezervationsJSONString = reservations.toString();
            POST.postResponse(exchange,rezervationsJSONString,200);
        }
        else {POST.postResponse(exchange,"Unauthorized",401);}
    }


    public static void addReservation(HttpExchange exchange) throws IOException, ParseException {
        boolean successfulLogin = false;
        JSONObject jsonObject = POST.postRequest(exchange);
        User user = new Gson().fromJson(jsonObject.toString(), User.class);
        NewReservation newReservation = new Gson().fromJson(jsonObject.toString(), NewReservation.class);
        User dbUser = DATABASE.getUserById(user); // DE ADAUGAT SA ARUNCE EROARE METODA DACA BAZA DE DATE INTOARCE NULL(userId = null -> user inexistent)
        boolean userMatch = user.verifyUser(dbUser);

        System.out.println(dbUser.toString()+" \n " + newReservation.toString());
        if (userMatch){
            Reservation addedReservation = null;
            try {
                addedReservation = DATABASE.addReservation(newReservation,dbUser);
            } catch (SQLException e) {
                System.out.println(e);
                POST.postResponse(exchange,"USER NOT FOUND",200);
            }
            String rezervationsJSONString = addedReservation.toString();
            POST.postResponse(exchange,rezervationsJSONString,200);
        }
        else {POST.postResponse(exchange,"Unauthorized",401);}
    }
}
