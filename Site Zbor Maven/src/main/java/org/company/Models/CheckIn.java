package org.company.Models;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class CheckIn {
    private int seatsToGenerate;
    private HashSet<String> usedSeats;
    private int max_airplane_seats;
    private HashSet<String> generatedSeats = new HashSet<>();
    private short rows;
    private HashSet<Passenger> passengers;
    int reservationId;


    public CheckIn(int seatsToGenerate, HashSet<String> usedSeats, int max_airplane_seats,HashSet<Passenger> passengers,int reservationId) {
        this.seatsToGenerate = seatsToGenerate;
        this.usedSeats = usedSeats;
        this.max_airplane_seats = max_airplane_seats;
        this.passengers = passengers;
        this.reservationId = reservationId;
        this.rows = (short) (max_airplane_seats/6);
    }

    public int getSeatsToGenerate() {
        return seatsToGenerate;
    }

    public HashSet<String> getUsedSeats() {
        return usedSeats;
    }

    public int getMax_airplane_seats() {
        return max_airplane_seats;
    }

    public short getRows() {
        return rows;
    }

    public HashSet<String> getGeneratedSeats() {
        return generatedSeats;
    }

    public HashSet<Passenger> getPassengers() {
        return passengers;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void generateSeatNumber(){
        boolean alreadyUsed = true;
        String firstSeat = new String();
        while(alreadyUsed) {
            firstSeat = generateFirstSeat();
            alreadyUsed = usedSeats.contains(firstSeat);
        }
        generatedSeats.add(firstSeat);
        short rowNr = 0;
        char character = firstSeat.charAt(firstSeat.length() - 1);
        try {
            rowNr = (short) NumberFormat.getInstance().parse(firstSeat).intValue();
        } catch (ParseException e) {
            System.out.println("Cannot parse INT value of firstSeat");;
        }
        for(short i=2; i<=seatsToGenerate;i++) {
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
                        if (rowNr<rows){
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
                        if (rowNr<rows){
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
                        if (rowNr<rows){
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
                            if (rowNr<rows){
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
                            if (rowNr<rows){
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
                            if (rowNr<rows){
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
                    default: {nextSeat= generateFirstSeat();}

                }
                alreadyUsed=(usedSeats.contains(nextSeat)||generatedSeats.contains(nextSeat));
                tryAtempt++;
            }
            generatedSeats.add(nextSeat);
        }


    }

    private String generateFirstSeat(){
        String seat = new String();
        short randomRow = (short)Math.floor(Math.random()*(1-rows+1)+rows);
        String chars = "ABCDEF";
        Random rnd = new Random();
        char c = chars.charAt(rnd.nextInt(chars.length()));
        seat = String.valueOf(randomRow)+c;
        return "12C";
    }








}
