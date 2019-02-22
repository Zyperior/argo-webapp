package main;

import http.server.JavaHTTPServer;

import java.io.IOException;
import java.net.ServerSocket;


public class Main {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            ServerSocket serverConnect = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");

            while (true) {
                JavaHTTPServer myServer = new JavaHTTPServer(serverConnect.accept());

                Thread thread = new Thread(myServer);
                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }
}
