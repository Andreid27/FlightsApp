package org.company;

import java.io.IOException;
import java.sql.SQLException;

import org.company.APImethods.HttpServerClass;


public class App
{ public static void main( String[] args ) throws IOException, SQLException {
        HttpServerClass.serverStart();
        DATABASE.startDBconnection();

    }
}
