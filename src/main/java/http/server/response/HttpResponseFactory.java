package http.server.response;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpResponseFactory {

    public static void createResponse(RequestType type, String fileRequested, PrintWriter out,
                                              BufferedOutputStream dataOut) throws IOException {

        switch (type) {
            case GET:
                new HttpResponseGet(fileRequested,out,dataOut);
            case HEAD:
                new HttpResponseHead(fileRequested,out,dataOut);
            case INVALID:
                new HttpResponseInvalid(fileRequested,out,dataOut);
            default:
                new HttpResponseInvalid(fileRequested,out,dataOut);
        }

    }
}
