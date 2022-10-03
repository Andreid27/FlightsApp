package org.company.APImethods;

import org.company.Services.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.InetSocketAddress;

//import static org.company.APImethods.GET.getMethod;


public class HttpServerClass {
    public static void serverStart() throws IOException {
        com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/login", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    LoginService.login(exchange);
                } catch (ParseException e) {
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
            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                OPTIONS.optionsMethod(exchange);

            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
        }));
        server.createContext("/api/rezervations", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                try {
                    ReservationsService.getReservationsByUserIdAndPassword(exchange);
                } catch (ParseException e) {
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
            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                OPTIONS.optionsMethod(exchange);

            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
        }));
        server.createContext("/api/destinations", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                try {
                    FlightsService.getDestinations(exchange);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                OPTIONS.optionsMethod(exchange);

            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
        }));
        server.createContext("/api/routeFlights", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    FlightsService.getRouteFlights(exchange);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                OPTIONS.optionsMethod(exchange);

            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
        }));


        server.createContext("/api/NewReservation", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    ReservationsService.addReservation(exchange);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                OPTIONS.optionsMethod(exchange);

            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
        }));
        server.createContext("/api/CheckIn", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    CheckInService.makeCheckIn(exchange);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                OPTIONS.optionsMethod(exchange);

            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
        }));

        server.createContext("/api/getCheckIn", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                try {
                    CheckInService.getCheckIn(exchange);
                } catch (ParseException | IOException e) {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
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

