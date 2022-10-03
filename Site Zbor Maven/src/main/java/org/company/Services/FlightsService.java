package org.company.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.company.APImethods.GET;
import org.company.APImethods.POST;
import org.company.dao.FlightDao;
import org.company.dao.UserDao;
import org.company.Models.Flight;
import org.company.Models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.company.APImethods.POST.postRequest;

public class FlightsService {
    public static void getUpcomingFlights(HttpExchange exchange) throws IOException, ParseException {
        Map<String,String> queryMap = GET.getRequest(exchange);
        User user = new User(Integer.parseInt(queryMap.get("userId")), queryMap.get("password"));
        Optional<User> user1 = UserDao.getUserById(user);
        User dbUser = user1.orElseGet(() -> new User(0,null));
        boolean userMatch = user.verifyUserIdAndPassword(dbUser);
        if (userMatch){
            ArrayList<Flight> flights = null;
            try {
                flights = FlightDao.getUpcomingFlights(dbUser);
            } catch (SQLException e) {
                GET.getResponse(exchange,"CANNOT ACCES DATABASE",200);
            }
            String fligthsJSONString = flights.toString();
            GET.getResponse(exchange,fligthsJSONString,200);
        }
        else {GET.getResponse(exchange,"Unauthorized",401);}
    }


    public static void getDestinations(HttpExchange exchange) throws IOException, ParseException {
        Map<String,String> queryMap = GET.getRequest(exchange);
        User user = new User(Integer.parseInt(queryMap.get("userId")), queryMap.get("password"));
        boolean userMatch = User.DBverifyUserByIdAndPassword(user);
        Map destinations = null;
        if (userMatch){
            try {
                destinations = FlightDao.getDestinations();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(destinations);
                GET.getResponse(exchange,json,200);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        else {GET.getResponse(exchange,"Unauthorized",401);}
    }
    public static void getRouteFlights(HttpExchange exchange) throws IOException, ParseException {
        JSONObject jsonObject = postRequest(exchange);
        User user = new Gson().fromJson(jsonObject.toString(), User.class);
        Flight flightRoute= new Gson().fromJson(jsonObject.toString(), Flight.class);
        boolean userMatch = User.DBverifyUserByIdAndPassword(user);
        ArrayList<Flight> RouteFlights = null;
        if (userMatch){
            try {
                RouteFlights = FlightDao.getFlightsByRoute(flightRoute);
                POST.postResponse(exchange, RouteFlights.toString(), 200);
            } catch (SQLException e) {
                POST.postResponse(exchange, "Database connection failed", 200);
            }
        }
        else {
            POST.postResponse(exchange, "Unauthorized", 401);}
    }




    }


