package org.company.Services;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.company.APImethods.POST;
import org.company.Controllers.CheckInControler;
import org.company.Controllers.ReservationController;
import org.company.Controllers.UserController;
import org.company.Models.CheckIn;
import org.company.Models.Passenger;
import org.company.Models.Reservation;
import org.company.Models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class CheckInService {
    public static void makeCheckIn(HttpExchange exchange) throws IOException, ParseException {
        JSONObject jsonObject = POST.postRequest(exchange);
        User user = new Gson().fromJson(jsonObject.toString(), User.class);
        Reservation reservationFromJson = new Gson().fromJson(jsonObject.toString(), Reservation.class);
        Optional<User> user1 = UserController.getUserById(user);
        User dbUser = user1.orElseGet(() -> new User(0,null));
        boolean userMatch = user.verifyUserIdAndPassword(dbUser);
        if (userMatch){
            Reservation addedReservation = null;
            try {
                CheckIn checkIn = CheckInControler.getRezervationByIdSeats(reservationFromJson.getId(),dbUser.getUserId());
                checkIn.generateSeatNumber();
                POST.postResponse(exchange,"AM AJUNS AICI",200);
            } catch (SQLException e) {
                System.out.println(e);
                POST.postResponse(exchange,"USER NOT FOUND",401);
            }
            String rezervationsJSONString = addedReservation.toString();
            POST.postResponse(exchange,rezervationsJSONString,200);
        }
        else {POST.postResponse(exchange,"Unauthorized",401);}
    }





}



