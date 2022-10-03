package org.company.Services;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.util.JDKXRLogger;
import com.openhtmltopdf.util.XRLog;
import com.openhtmltopdf.util.XRLogger;
import org.company.Controllers.CheckInController;
import org.company.Models.CheckIn;
import org.company.Models.Passenger;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class CheckInService {


    public static CheckIn generateSeatNumber(CheckIn checkIn){
        boolean alreadyUsed = true;
        HashSet<String> generatedSeats = new HashSet<>();
        String firstSeat = new String();
        while(alreadyUsed) {
            firstSeat = generateFirstSeat(checkIn);
            alreadyUsed = false;
            alreadyUsed = checkIn.getUsedSeats().contains(firstSeat);
        }
        generatedSeats.add(firstSeat);
        short rowNr = 0;
        char character = firstSeat.charAt(firstSeat.length() - 1);
        try {
            rowNr = (short) NumberFormat.getInstance().parse(firstSeat).intValue();
        } catch (ParseException e) {
            System.out.println("Cannot parse INT value of firstSeat");;
        }
        short i;
        for(i = 2; i<= checkIn.getSeatsToGenerate(); i++) {
            alreadyUsed = true;
            short tryAtempt = 0;
            String nextSeat = new String();
            while (alreadyUsed) {
                switch (tryAtempt) {
                    case (0): {
                        if (Character.compare(character, 'C') <= 0 && Character.compare(character, 'A') > 0) {
                            char characterCopy = (char) (character-1);
                            nextSeat= String.valueOf(rowNr)+characterCopy;
                        }
                        else if (Character.compare(character, 'F') < 0){
                            char characterCopy = (char) (character+1);
                            nextSeat= String.valueOf(rowNr)+characterCopy;
                        }
                        else {
                            char characterCopy = (char) (character-1);
                            nextSeat= String.valueOf(rowNr)+characterCopy;
                        }
                        break;
                    }
                    case (1):{
                        if (Character.compare(character, 'C') <= 0 && Character.compare(character, 'B') > 0) {
                            char characterCopy = (char) (character-2);
                            nextSeat= String.valueOf(rowNr)+characterCopy;
                        }
                        else if (Character.compare(character, 'E') < 0){
                            char characterCopy = (char) (character+2);
                            nextSeat= String.valueOf(rowNr)+characterCopy;
                        }
                        else {
                            char characterCopy = (char) (character-2);
                            nextSeat= String.valueOf(rowNr)+characterCopy;
                        }
                        break;
                    }
                    case (2):{
                        if (Character.compare(character, 'C') <= 0) { // daca <c -> du-te o pozitie in dreapta
                            char characterCopy = (char) (character+1);
                            nextSeat= String.valueOf(rowNr)+characterCopy;
                        }
                        else if (Character.compare(character, 'F') <= 0){ //DACA char<F -> du-te o pozitie in stanga
                            char characterCopy = (char) (character-1);
                            nextSeat= String.valueOf(rowNr)+characterCopy;
                        }
                        break;
                    }
                    case (3):{
                        if (Character.compare(character, 'C') <= 0) { // daca <c -> du-te o pozitie in dreapta
                            char characterCopy = (char) (character+2);
                            nextSeat= String.valueOf(rowNr)+characterCopy;
                        }
                        else if (Character.compare(character, 'F') <= 0){ //DACA char<F -> du-te o pozitie in stanga
                            char characterCopy = (char) (character-2);
                            nextSeat= String.valueOf(rowNr)+characterCopy;
                        }
                        break;
                    }
                    case (4):{
                        if (Character.compare(character, 'C') <= 0) { // daca <c -> du-te o pozitie in dreapta
                            char characterCopy = (char) (character+3);
                            nextSeat= String.valueOf(rowNr)+characterCopy;
                        }
                        else if (Character.compare(character, 'F') <= 0){ //DACA char<F -> du-te o pozitie in stanga
                            char characterCopy = (char) (character-3);
                            nextSeat= String.valueOf(rowNr)+characterCopy;
                        }
                        break;
                    }
                    case (5):{
                        short rowCopy = 0;
                        if (rowNr< checkIn.getRows()){
                            rowCopy = (short) (rowNr+1);
                        }
                        nextSeat= String.valueOf(rowCopy)+character;
                    }
                    case (6):{
                        short rowCopy = 0;
                        if (rowNr>1){
                            rowCopy = (short) (rowNr-1);
                        }
                        nextSeat= String.valueOf(rowCopy)+character;
                    }
                    case (7):{
                        short rowCopy = 0;
                        if (rowNr>1){
                            rowCopy = (short) (rowNr-1);
                        }
                        if (Character.compare(character, 'C') <= 0 && Character.compare(character, 'A') > 0) {
                            char characterCopy = (char) (character-1);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else if (Character.compare(character, 'F') < 0){
                            char characterCopy = (char) (character+1);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else {
                            char characterCopy = (char) (character-1);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        break;
                    }
                    case (8):{
                        short rowCopy = 0;
                        if (rowNr>1){
                            rowCopy = (short) (rowNr-1);
                        }
                        if (Character.compare(character, 'C') <= 0 && Character.compare(character, 'B') > 0) {
                            char characterCopy = (char) (character-2);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else if (Character.compare(character, 'E') < 0){
                            char characterCopy = (char) (character+2);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else {
                            char characterCopy = (char) (character-2);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        break;
                    }
                    case (9):{
                        short rowCopy = 0;
                        if (rowNr>1){
                            rowCopy = (short) (rowNr-1);
                        }
                        if (Character.compare(character, 'C') <= 0) { // daca <c -> du-te o pozitie in dreapta
                            char characterCopy = (char) (character+1);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else if (Character.compare(character, 'F') <= 0){ //DACA char<F -> du-te o pozitie in stanga
                            char characterCopy = (char) (character-1);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        break;
                    }
                    case (10):{
                        short rowCopy = 0;
                        if (rowNr>1){
                            rowCopy = (short) (rowNr-1);
                        }
                        if (Character.compare(character, 'C') <= 0) { // daca <c -> du-te o pozitie in dreapta
                            char characterCopy = (char) (character+2);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else if (Character.compare(character, 'F') <= 0){ //DACA char<F -> du-te o pozitie in stanga
                            char characterCopy = (char) (character-2);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        break;
                    }
                    case (11):{
                        short rowCopy = 0;
                        if (rowNr>1){
                            rowCopy = (short) (rowNr-1);
                        }
                        if (Character.compare(character, 'C') <= 0) { // daca <c -> du-te o pozitie in dreapta
                            char characterCopy = (char) (character+3);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else if (Character.compare(character, 'F') <= 0){ //DACA char<F -> du-te o pozitie in stanga
                            char characterCopy = (char) (character-3);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        break;
                    }
                    case (12):{
                        short rowCopy = 0;
                        if (rowNr< checkIn.getRows()){
                            rowCopy = (short) (rowNr+1);
                        }
                        if (Character.compare(character, 'C') <= 0 && Character.compare(character, 'A') > 0) {
                            char characterCopy = (char) (character-1);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else if (Character.compare(character, 'F') < 0){
                            char characterCopy = (char) (character+1);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else {
                            char characterCopy = (char) (character-1);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        break;
                    }
                    case (13):{
                        short rowCopy = 0;
                        if (rowNr< checkIn.getRows()){
                            rowCopy = (short) (rowNr+1);
                        }
                        if (Character.compare(character, 'C') <= 0 && Character.compare(character, 'B') > 0) {
                            char characterCopy = (char) (character-2);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else if (Character.compare(character, 'E') < 0){
                            char characterCopy = (char) (character+2);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else {
                            char characterCopy = (char) (character-2);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        break;
                    }
                    case (14):{
                        short rowCopy = 0;
                        if (rowNr< checkIn.getRows()){
                            rowCopy = (short) (rowNr+1);
                        }
                        if (Character.compare(character, 'C') <= 0) { // daca <c -> du-te o pozitie in dreapta
                            char characterCopy = (char) (character+1);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else if (Character.compare(character, 'F') <= 0){ //DACA char<F -> du-te o pozitie in stanga
                            char characterCopy = (char) (character-1);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        break;
                    }
                    case (15):{
                        short rowCopy = 0;
                        if (rowNr< checkIn.getRows()){
                            rowCopy = (short) (rowNr+1);
                        }
                        if (Character.compare(character, 'C') <= 0) { // daca <c -> du-te o pozitie in dreapta
                            char characterCopy = (char) (character+2);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else if (Character.compare(character, 'F') <= 0){ //DACA char<F -> du-te o pozitie in stanga
                            char characterCopy = (char) (character-2);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        break;
                    }
                    case (16):{
                        short rowCopy = 0;
                        if (rowNr< checkIn.getRows()){
                            rowCopy = (short) (rowNr+1);
                        }
                        if (Character.compare(character, 'C') <= 0) { // daca <c -> du-te o pozitie in dreapta
                            char characterCopy = (char) (character+3);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        else if (Character.compare(character, 'F') <= 0){ //DACA char<F -> du-te o pozitie in stanga
                            char characterCopy = (char) (character-3);
                            nextSeat= String.valueOf(rowCopy)+characterCopy;
                        }
                        break;
                    }
                    default: {nextSeat= generateFirstSeat(checkIn);}

                }
                alreadyUsed=(checkIn.getUsedSeats().contains(nextSeat)|| generatedSeats.contains(nextSeat));
                tryAtempt++;
            }
            generatedSeats.add(nextSeat);
        }
        return new CheckIn(checkIn.getSeatsToGenerate(),checkIn.getUsedSeats(),checkIn.getMax_airplane_seats(),generatedSeats,checkIn.getRows(),checkIn.getPassengers(),checkIn.getReservationId(),checkIn.getFlight());
    }

    private static String generateFirstSeat(CheckIn checkIn){
        String seat = new String();
        short randomRow = (short)Math.floor(Math.random()*(1- checkIn.getRows()+1)+checkIn.getRows());
        String chars = "ABCDEF";
        Random rnd = new Random();
        char c = chars.charAt(rnd.nextInt(chars.length()));
        seat = String.valueOf(randomRow)+c;
        return seat;
    }








    public static void createFile(CheckIn checkIn, String intputPath, String outputPath) throws IOException {
        init();//Hide htmlToPdf INFO
        String htmlString = CheckInService.htmlModfiedString(checkIn,intputPath);
        CheckInService.htmlToPdf(htmlString,outputPath);

    }

    public static String htmlModfiedString(CheckIn checkIn,String templatePath) throws IOException {
        Path template = Paths.get(templatePath);
        Passenger[]  passengers = checkIn.getPassengers().toArray(new Passenger[checkIn.getPassengers().size()]);
        Passenger passenger = passengers[0];
        String htmlTemplate = new String();
        BufferedReader br = new BufferedReader(Files.newBufferedReader(template));
        try(br) {
            String line = br.readLine();
            List<String> list = new ArrayList<>();
            while (line != null) {
                if (line.contains("|departure_location|") || line.contains("|landing_location|") || line.contains("|departure_time|") || line.contains("|landing_time|") || line.contains("|company|") || line.contains("|airplane|")
                        || line.contains("|first_name|") || line.contains("|last_name|") || line.contains("|id|") || line.contains("|seat|") || line.contains("|flight_number|")) {
                    if (line.contains("|departure_location|")) {
                        line = line.replace("|departure_location|", checkIn.getFlight().getDepartureLocation());
                    } else if (line.contains("|departure_time|")) {
                        line = line.replace("|departure_time|", checkIn.getFlight().getDepartureTimestamp());
                    } else if (line.contains("|landing_location|")) {
                        line = line.replace("|landing_location|", checkIn.getFlight().getLandingLocation());
                    } else if (line.contains("|landing_time|")) {
                        line = line.replace("|landing_time|", checkIn.getFlight().getLandingTimestamp());
                    } else if (line.contains("|flight_number|")) {
                        line = line.replace("|flight_number|", checkIn.getFlight().getFlightNumber());
                    } else if (line.contains("|company|")) {
                        line = line.replace("|company|", checkIn.getFlight().getCompany());
                    } else if (line.contains("|airplane|")) {
                        line = line.replace("|airplane|", (checkIn.getFlight().getAirplane().getBrand() + " " + checkIn.getFlight().getAirplane().getModel()));
                    } else if (line.contains("|first_name|")) {
                        line = line.replace("|first_name|", passenger.getName());
                    } else if (line.contains("|last_name|")) {
                        line = line.replace("|last_name|", passenger.getSurname());
                    } else if (line.contains("|seat|")) {
                        line = line.replace("|seat|", passenger.getSeatNumber());
                    } else if (line.contains("|id|")) {
                        line = line.replace("|id|", passenger.getIdentificationNumber());
                    }
                }
                list.add(line);
                line = br.readLine();
            }
            htmlTemplate = String.join(" ", list);
            return htmlTemplate;
        }
    }



    public static void htmlToPdf(String htmlString,String fileOutPath) throws IOException {
        var renderedPdfBytes = new ByteArrayOutputStream();
        var builder = new PdfRendererBuilder();
        builder.withHtmlContent(htmlString, "G:/Desktop/CURS JAVA 1 - Professional/PROBLEME EXTRA/Site Zbor Maven/data/template.html");
        builder.toStream(renderedPdfBytes);
        builder.run();
        renderedPdfBytes.close();
        var renderedPdf = renderedPdfBytes.toByteArray();

        try (var fos = new FileOutputStream(fileOutPath)) {
            fos.write(renderedPdf);
        }
    }



    /////////////////////////////THIS FUNCTION STOPS LOGGING THE HTMLtoPDF INFO to console everytime/////////////////////////////////////
    private static volatile boolean initPending = true;
    private static volatile XRLogger loggerImpl;
    private static volatile Boolean loggingEnabled;
    private static void init() {
        synchronized (XRLog.class) {
            if (!initPending) {
                return;
            }

            if (loggingEnabled == null) {
                XRLog.setLoggingEnabled(false);
            }

            if (loggerImpl == null) {
                loggerImpl = new JDKXRLogger();
            }

            initPending = false;
        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }
}
