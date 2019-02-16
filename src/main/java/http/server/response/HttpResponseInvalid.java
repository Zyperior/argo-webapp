package http.server.response;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpResponseInvalid extends HttpResponse{

    HttpResponseInvalid(String fileRequested, PrintWriter out, BufferedOutputStream dataOut) throws IOException {
        super(fileRequested, out, dataOut);
        this.body = true;
        sendResponse();
    }

    public void sendResponse() throws IOException {
        if(fileNotFound){
            fileNotFound();
        }
        else{
            out.println("HTTP/1.1 501 Not Implemented");
            standardOutToClient();
        }

    }
}
