package http.server.response;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HttpResponseGet extends HttpResponse {

    HttpResponseGet(String fileRequested, PrintWriter out, BufferedOutputStream dataOut,ArrayList<String> requestData) throws IOException {
        super(fileRequested,out,dataOut, requestData);
        this.body = true;
        sendResponse();
    }

    public void sendResponse() throws IOException {
       if(fileNotFound){
           fileNotFound();
      }
       else{
    	   out.println("HTTP/1.1 200 OK");
            standardOutToClient();
        }

    }
}
