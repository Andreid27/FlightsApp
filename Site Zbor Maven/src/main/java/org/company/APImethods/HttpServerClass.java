package org.company.APImethods;

import org.company.Services.LoginService;
import org.company.Services.RegisterService;
import org.company.Services.ReservationsService;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

import static org.company.APImethods.GET.getMethod;


public class HttpServerClass {
    public static void serverStart() throws IOException {
        com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/get", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                getMethod(exchange);
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

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
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    ReservationsService.reservation(exchange);
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

