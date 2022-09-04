package org.company.Services;

import com.sun.net.httpserver.HttpExchange;
import org.apache.http.protocol.HttpContext;
import org.company.APImethods.GET;
import org.company.APImethods.POST;
import org.company.DATABASE;
import org.company.Models.Flight;
import org.company.Models.Reservation;
import org.company.Models.User;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class FlightsService {
    public static void getUpcomingFlights(HttpExchange exchange) throws SQLException, IOException, ParseException {
        Map<String,String> queryMap = GET.getRequest(exchange);
        User user = new User(Integer.parseInt(queryMap.get("userId")), queryMap.get("password"));
        User dbUser = DATABASE.getUserById(user);
        boolean userMatch = user.verifyUser(dbUser);
        if (userMatch){
            ArrayList<Flight> flights = DATABASE.getUpcomingFlights(dbUser);
            String fligthsJSONString = flights.toString();
            GET.getResponse(exchange,fligthsJSONString,200);
        }
        else {GET.getResponse(exchange,"Unauthorized",401);}
    }
    }


