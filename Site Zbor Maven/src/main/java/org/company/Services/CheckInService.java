package org.company.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.sun.net.httpserver.HttpExchange;
import org.company.APImethods.GET;
import org.company.APImethods.POST;
import org.company.Controllers.CheckInControler;
import org.company.Controllers.FlightController;
import org.company.Controllers.ReservationController;
import org.company.Controllers.UserController;
import org.company.Models.CheckIn;
import org.company.Models.Passenger;
import org.company.Models.Reservation;
import org.company.Models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Array;
import java.sql.SQLException;
import java.util.*;

public class CheckInService {
    public static void makeCheckIn(HttpExchange exchange) throws IOException, ParseException {
        JSONObject jsonObject = POST.postRequest(exchange);
        User user = new Gson().fromJson(jsonObject.toString(), User.class);
        CheckIn checkIn = new Gson().fromJson(jsonObject.toString(), CheckIn.class);
        Optional<User> user1 = UserController.getUserById(user);
        User dbUser = user1.orElseGet(() -> new User(0,null));
        boolean userMatch = user.verifyUserIdAndPassword(dbUser);
        if (userMatch){
            try {
                checkIn = CheckInControler.getRezervationByIdSeats(checkIn,dbUser.getUserId());
                checkIn.generateSeatNumber();
                if(checkIn.getPassengers().size()== checkIn.getSeatsToGenerate()){
                    if(CheckInControler.addCinDB(checkIn)){
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
        Map destinations = null;
        if (userMatch){
            try {
                CheckIn checkIn = CheckInControler.getCheckIn(queryMap.get("reservationId"));
                CheckInService.createFile(checkIn);
                GET.getResponse(exchange, GET.getFile("G:/Desktop/CURS JAVA 1 - Professional/ticket-1-Day-Pass-Andrei-Gabriel-Dinca.pdf"),200);
            } catch (SQLException e) {
                GET.getResponse(exchange,"DATABASES ERROR",405);
            }
        }
        else {GET.getResponse(exchange,"Unauthorized",401);}

    }






    public static void createFile(CheckIn checkIn) throws IOException {
        var renderedPdfBytes = new ByteArrayOutputStream();
        var builder = new PdfRendererBuilder();
        Path template = Paths.get("data/template.html");
        Path usedTemplate = Paths.get("data/usedTemplate.html");

        if(Files.exists(usedTemplate)){
            Files.delete(usedTemplate);
            Files.copy(template,usedTemplate );
        } else {
        Files.copy(template,usedTemplate );
        }
       Passenger[]  passengers = checkIn.getPassengers().toArray(new Passenger[checkIn.getPassengers().size()]);
        Passenger passenger = passengers[0];
        String htmlTemplate = new String();
        BufferedReader br = new BufferedReader(Files.newBufferedReader(usedTemplate));
        try(br){
            String line = br.readLine();
            List<String> list = new ArrayList<>();
                while (line != null){
                    if (line.contains("|departure_location|")||line.contains("|landing_location|")||line.contains("|departure_time|")||line.contains("|landing_time|") ||line.contains("|company|") ||line.contains("|airplane|")
                            ||line.contains("|first_name|")||line.contains("|last_name|")||line.contains("|id|")||line.contains("|seat|")||line.contains("|flight_number|")) {
                        if (line.contains("|departure_location|")) {
                            line = line.replace("|departure_location|",checkIn.getFlight().getDepartureLocation());
                        } else if (line.contains("|departure_time|")) {
                            line = line.replace("|departure_time|",checkIn.getFlight().getDepartureTimestamp());
                        } else if (line.contains("|landing_location|")) {
                            line = line.replace("|landing_location|",checkIn.getFlight().getLandingLocation());
                        } else if (line.contains("|landing_time|")) {
                            line = line.replace("|landing_time|",checkIn.getFlight().getLandingTimestamp());
                        } else if (line.contains("|flight_number|")) {
                            line = line.replace("|flight_number|",checkIn.getFlight().getFlightNumber());
                        } else if (line.contains("|company|")) {
                        line = line.replace("|company|",checkIn.getFlight().getCompany());
                        } else if (line.contains("|airplane|")) {
                        line = line.replace("|airplane|",(checkIn.getFlight().getAirplane().getBrand()+" "+checkIn.getFlight().getAirplane().getModel()));
                        } else if (line.contains("|first_name|")) {
                            line = line.replace("|first_name|",passenger.getName());
                        } else if (line.contains("|last_name|")) {
                            line = line.replace("|last_name|",passenger.getSurname());
                        } else if (line.contains("|seat|")) {
                            line = line.replace("|seat|",passenger.getSeatNumber());
                        } else if (line.contains("|id|")) {
                            line = line.replace("|id|",passenger.getIdentificationNumber());
                        }
                    }
                list.add(line);
                line= br.readLine();
            }
                htmlTemplate = String.join(" ",list);
        }

        builder.withHtmlContent(htmlTemplate, "G:/Desktop/CURS JAVA 1 - Professional/PROBLEME EXTRA/Site Zbor Maven/data/template.html");
        builder.toStream(renderedPdfBytes);
        builder.run();
        renderedPdfBytes.close();
        var renderedPdf = renderedPdfBytes.toByteArray();

        try (var fos = new FileOutputStream("example.pdf")) {
            fos.write(renderedPdf);
        }
    }


}



