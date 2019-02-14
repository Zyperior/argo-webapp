package http.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

// The tutorial can be found just here on the SSaurel's Blog : 
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// Each Client Connection will be managed in a dedicated Thread
public class JavaHTTPServer implements Runnable{


    // port to listen connection


    // Client Connection via Socket Class
    private Socket connect;

    public JavaHTTPServer(Socket c) {
        connect = c;
    }

    public void run() {
        // we manage our particular client connection
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        RequestType type = RequestType.INVALID;
        String fileRequested = null;

        try {
            // we read characters from the client via input stream on the socket
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            // we get character output stream to client (for headers)
            out = new PrintWriter(connect.getOutputStream());
            // get binary output stream to client (for requested data)
            dataOut = new BufferedOutputStream(connect.getOutputStream());

            // get first line of the request from the client
            String input = in.readLine();
            // we parse the request with a string tokenizer
            StringTokenizer parse = new StringTokenizer(input);
            String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
            // we get file requested
            fileRequested = parse.nextToken().toLowerCase();

            if(method.equals("GET")){
                type = RequestType.GET;
            }
            else if(method.equals("HEAD")){
                type = RequestType.HEAD;
            }
            else if(method.equals("POST")){
                type = RequestType.POST;
            }

            new HTTPRequest(type,fileRequested,out,dataOut);


        } catch (IOException ioe) {

            System.err.println("Server error : " + ioe);

        } finally {

            try {
                in.close();
                out.close();
                dataOut.close();
                connect.close(); // we close socket connection
            } catch (Exception e) {
                System.err.println("Error closing stream : " + e.getMessage());
            }

        }


    }



    // return supported MIME Types




}