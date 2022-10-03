package org.company.Controllers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.company.APImethods.GET;
import org.company.APImethods.POST;
import org.company.Services.CheckInService;
import org.company.dao.CheckInDao;
import org.company.dao.UserDao;
import org.company.Models.CheckIn;
import org.company.Models.Passenger;
import org.company.Models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import static org.company.Services.CheckInService.generateSeatNumber;

public class CheckInController {
    public static void makeCheckIn(HttpExchange exchange) throws IOException, ParseException {
        JSONObject jsonObject = POST.postRequest(exchange);
        User user = new Gson().fromJson(jsonObject.toString(), User.class);
        CheckIn checkIn = new Gson().fromJson(jsonObject.toString(), CheckIn.class);
        Optional<User> user1 = UserDao.getUserById(user);
        User dbUser = user1.orElseGet(() -> new User(0,null));
        boolean userMatch = user.verifyUserIdAndPassword(dbUser);
        if (userMatch){
            try {
                checkIn = CheckInDao.getRezervationByIdSeats(checkIn,dbUser.getUserId());
                checkIn = generateSeatNumber(checkIn);
                if(checkIn.getPassengers().size()== checkIn.getSeatsToGenerate()){
                    if(CheckInDao.addCinDB(checkIn)){
                    POST.postResponse(exchange,"SUCCES",200);
                    }
                    else {
                        POST.postResponse(exchange,"DATABASE ERROR",200);
                    }
                }

            } catch (SQLException e) {
                System.out.println(e);
                POST.postResponse(exchange,"USER NOT FOUND",401);
            }
        }
        else {POST.postResponse(exchange,"Unauthorized",401);}
    }


    public static void getCheckIn(HttpExchange exchange) throws IOException, ParseException {
        Map<String,String> queryMap = GET.getRequest(exchange);
        User user = new User(Integer.parseInt(queryMap.get("userId")), queryMap.get("password"));
        boolean userMatch = User.DBverifyUserByIdAndPassword(user);
        if (userMatch){
            try {
                CheckIn checkIn = CheckInDao.getCheckIn(queryMap.get("reservationId"));
                CheckInService.createFile(checkIn,"data/template.html","./data/temp/boarding.pdf");
                GET.getResponse(exchange, GET.getFile("./data/temp/boarding.pdf"),200);
            } catch (SQLException e) {
                GET.getResponse(exchange,"DATABASES ERROR",405);
            }
        }
        else {GET.getResponse(exchange,"Unauthorized",401);}

    }








}



