package http.server.response;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HttpResponseFactory {

	public static void createResponse(RequestType type, String fileRequested, PrintWriter out,
			BufferedOutputStream dataOut, ArrayList<String> requestData) throws IOException {

		switch (type) {
		case GET:
			new HttpResponseGet(fileRequested, out, dataOut, requestData);
			break;
		case HEAD:
			new HttpResponseHead(fileRequested, out, dataOut, requestData);
			break;
		case POST:
			new HttpResponsePost(fileRequested, out, dataOut, requestData);
			break;
		case INVALID:
			new HttpResponseInvalid(fileRequested, out, dataOut, requestData);
			break;
		default:
			new HttpResponseInvalid(fileRequested, out, dataOut, requestData);
			break;
		}

	}
}
