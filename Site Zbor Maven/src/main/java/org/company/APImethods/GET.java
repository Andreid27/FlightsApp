package org.company.APImethods;

import com.sun.net.httpserver.HttpExchange;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GET {
    public static Map getRequest(HttpExchange exchange) {
        Map getMap = queryToMap(exchange.getRequestURI().getQuery());
        return getMap;
    }


    public static Map<String, String> queryToMap(String query) {
        if (query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }

    public static void getResponse(HttpExchange exchange, String response, int rCode) throws IOException, ParseException {
        String responseText = response;//"The post was completed";

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "*");
        exchange.getResponseHeaders().set("Access-Control-Max-Age", "1728000");
        exchange.getResponseHeaders().set("Connection", "Keep-Alive");
        exchange.sendResponseHeaders(200, responseText.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(responseText.getBytes());
        output.flush();
        output.close();
    }

//    public static void getPdfResponse(HttpExchange exchange, String response, int rCode) throws IOException, ParseException {
//        String responseText = response;//"The post was completed";
//
//        exchange.getResponseHeaders().set("Content-Type", "application/json");
//        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
//        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "*");
//        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "*");
//        exchange.getResponseHeaders().set("Access-Control-Max-Age", "1728000");
//        exchange.getResponseHeaders().set("Connection", "Keep-Alive");
//        exchange.sendResponseHeaders(rCode, responseText.getBytes().length);
//        OutputStream output = exchange.getResponseBody();
//        output.write(responseText.getBytes());
//        output.flush();
//        output.close();
//    }


}
