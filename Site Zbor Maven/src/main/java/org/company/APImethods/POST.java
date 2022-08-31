package org.company.APImethods;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.company.Models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class POST {
    public static JSONObject postRequest(HttpExchange exchange) throws IOException, ParseException {
        InputStream e = exchange.getRequestBody();
        JSONObject jsonObject = readRequest(e);
        return jsonObject;
    }

    protected static JSONObject readRequest(InputStream is) throws IOException, ParseException {
        String text = null;
        JSONObject jsonObject;
        try {
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject)jsonParser.parse(
                    new InputStreamReader(is, "UTF-8"));
        }
        catch (IOException ex){
            throw new IOException(ex);
        }
        //System.out.println(jsonObject);
        return jsonObject;
    }

    public static void postResponse(HttpExchange exchange, String response, int rCode) throws IOException, ParseException {
        String responseText = response;//"The post was completed";

        try {
            exchange.getResponseHeaders().set("Content-Type","application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin","*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods","*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers","*");
            exchange.getResponseHeaders().set("Access-Control-Max-Age","1728000");
            exchange.getResponseHeaders().set("Connection","Keep-Alive");
            exchange.sendResponseHeaders(rCode, responseText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(responseText.getBytes());
            output.flush();
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
