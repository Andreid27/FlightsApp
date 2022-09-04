package org.company.APImethods;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import org.apache.http.protocol.HttpContext;
import org.company.Services.FlightsService;
import org.company.Services.LoginService;
import org.company.Services.RegisterService;
import org.company.Services.ReservationsService;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.Map;

//import static org.company.APImethods.GET.getMethod;
import static org.company.APImethods.POST.postRequest;


public class HttpServerClass {
    public static void serverStart() throws IOException {
        com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/login", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    LoginService.login(exchange);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                OPTIONS.optionsMethod(exchange);

            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
        }));
        server.createContext("/api/register", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    RegisterService.register(exchange);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
//                catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                OPTIONS.optionsMethod(exchange);

            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
        }));
        server.createContext("/api/rezervations", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                try {
                    ReservationsService.getReservation(exchange);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                OPTIONS.optionsMethod(exchange);

            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
        }));

        server.createContext("/api/flights", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                try {
                    FlightsService.getUpcomingFlights(exchange);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                OPTIONS.optionsMethod(exchange);

            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
        }));



        server.setExecutor(null); // creates a default executor
        server.start();
    }

}

