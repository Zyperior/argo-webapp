package main;

import http.server.JavaHTTPServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

public class Main {

	private static int PORT = 8080;

	public static void main(String[] args) {
		try {

			if (new File("config.xml").isFile() == true) {
				Properties serverSettings = new Properties();

				FileInputStream in = new FileInputStream("config.xml");

				serverSettings.loadFromXML(in);
				String hold = serverSettings.getProperty("server_port");
				try {
					PORT = Integer.parseInt(hold);
					if (PORT < 8000 || PORT > 8089) {
						PORT = 8080;
					}
				} catch (Exception e) {

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

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
