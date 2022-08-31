package org.company.APImethods;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class OPTIONS {
    public static void optionsMethod(HttpExchange exchange){
        String responseText = "success";
        try {
            exchange.getResponseHeaders().set("Content-Type","application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin","http://localhost:8080");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods","POST, GET, OPTIONS, HEAD");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers","X-Requested-With");
            exchange.getResponseHeaders().set("Access-Control-Max-Age","1728000");
            exchange.getResponseHeaders().set("Connection","Keep-Alive");
            exchange.sendResponseHeaders(200, responseText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(responseText.getBytes());
            output.flush();
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


