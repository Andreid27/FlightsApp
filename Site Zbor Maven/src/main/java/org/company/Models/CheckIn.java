package org.company.Models;

import java.util.ArrayList;
import java.util.Random;

public class CheckIn {
    private int seatsToGenerate;
    private ArrayList<String> usedSeats;
    private int max_airplane_seats;
    private ArrayList<String> generatedSeats;
    private short rows;

    public CheckIn(int seatsToGenerate, ArrayList<String> usedSeats, int max_airplane_seats) {
        this.seatsToGenerate = seatsToGenerate;
        this.usedSeats = usedSeats;
        this.max_airplane_seats = max_airplane_seats;
        this.rows = (short) (max_airplane_seats/6);
    }

    public int getSeatsToGenerate() {
        return seatsToGenerate;
    }

    public ArrayList<String> getUsedSeats() {
        return usedSeats;
    }

    public int getMax_airplane_seats() {
        return max_airplane_seats;
    }

    public short getRows() {
        return rows;
    }

    public ArrayList<String> getGeneratedSeats() {
        return generatedSeats;
    }

    public void setGeneratedSeats(ArrayList<String> generatedSeats) {
        this.generatedSeats = generatedSeats;
    }



    public void generateSeatNumber(){
        boolean alreadyUsed = true;
        String firstSeat = new String();
        while(alreadyUsed) {
            firstSeat = generateFirstSeat();
            String finalFirstSeat = firstSeat;
            alreadyUsed = usedSeats.stream()
                    .anyMatch(usedSeat -> usedSeat.equals(finalFirstSeat));
            System.out.println(firstSeat);
        }
        generatedSeats.add(firstSeat);

//        DE CONTINUAT LOGICA PENRTU URMATOARELE LOCURI(pt a fi la rand)
//        primul este generat si nu se repeta



    }

    private String generateFirstSeat(){
        String seat = new String();
        short randomRow = (short)Math.floor(Math.random()*(1-rows+1)+rows);
        String chars = "ABCDEF";
        Random rnd = new Random();
        char c = chars.charAt(rnd.nextInt(chars.length()));
        seat = String.valueOf(randomRow)+c;
        return seat;
    }


}
