package org.company.APImethods;

import org.company.Controllers.*;
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
                    LoginController.login(exchange);
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
                    RegisterController.register(exchange);
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
                    ReservationsController.getReservationsByUserIdAndPassword(exchange);
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
                    FlightsController.getUpcomingFlights(exchange);
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
                    FlightsController.getDestinations(exchange);
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
                    FlightsController.getRouteFlights(exchange);
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
                    ReservationsController.addReservation(exchange);
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
                    CheckInController.makeCheckIn(exchange);
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
                    CheckInController.getCheckIn(exchange);
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

