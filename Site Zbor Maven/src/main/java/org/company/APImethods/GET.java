package org.company.APImethods;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class GET {
    public static void getMethod(HttpExchange exchange){
        String responseText = "Hello World! from our framework-less REST API";
        try {
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
