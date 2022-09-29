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
//                Map<String,String> file = CheckInService.getFile();
                CheckInService.createFile();
                GET.getResponse(exchange, CheckInService.getFile(),200);

        }
        else {GET.getResponse(exchange,"Unauthorized",401);}

    }


    public static String getFile() throws IOException {
        Map<String,String> file=new HashMap<>();
        Path path = Paths.get("G:/Desktop/CURS JAVA 1 - Professional/ticket-1-Day-Pass-Andrei-Gabriel-Dinca.pdf");
        System.out.println(Files.exists(path));

        byte[] inFileBytes = Files.readAllBytes(path);
        byte[] encoded = java.util.Base64.getEncoder().encode(inFileBytes);
//        file.put(String.valueOf(path.getFileName()),new String(encoded));
        file.put("",new String(encoded));

        return new String(encoded);
    }



    public static void createFile() throws IOException {
        var renderedPdfBytes = new ByteArrayOutputStream();
        var builder = new PdfRendererBuilder();
        Path template = Paths.get("data/template.html");
        Path usedTemplate = Paths.get("data/usedTemplate.html");

        if(Files.exists(usedTemplate)){
            Files.delete(usedTemplate);
            Files.copy(template,usedTemplate );
        }
        else {
        Files.copy(template,usedTemplate );
        }

        BufferedReader br = new BufferedReader(Files.newBufferedReader(usedTemplate));
        try(br){
            String line = br.readLine();
            List<String> list = new ArrayList<>();
            while (line != null){
                if (line.contains("|departure_location|")||line.contains("|landing_location|")||line.contains("|departure_time|")||line.contains("|landing_time|")
                ||line.contains("|first_name|")||line.contains("|last_name|")||line.contains("|id|")||line.contains("|seat|")||line.contains("|flight_number|")){

                }
                list.add(line);
                line= br.readLine();
            }
        }

        builder.withHtmlContent("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>Hello, World!</title>\n" +
                "  <style>\n" +
                "    h1 {\n" +
                "      text-decoration: underline;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <h1>Hello, World!</h1>\n" +
                "  <p>Lorem ipsum dolor sit amet.</p>\n" +
                "</body>\n" +
                "\n" +
                "</html>", "G:/Desktop/CURS JAVA 1 - Professional/PROBLEME EXTRA/Site Zbor Maven/data/template.html");
        builder.toStream(renderedPdfBytes);
        builder.run();
        renderedPdfBytes.close();
        var renderedPdf = renderedPdfBytes.toByteArray();

        try (var fos = new FileOutputStream("example.pdf")) {
            fos.write(renderedPdf);
        }
    }


}



