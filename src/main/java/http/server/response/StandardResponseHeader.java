package http.server.response;

/**
 * Enum for standard response headers
 * Used to set correct headers for the corresponding respons
 *
 * Created by Andreas Albihn, 2019-02-18
 */
public enum StandardResponseHeader {
    OK_200("HTTP/1.1 200 OK"),
    CREATED_201("HTTP/1.1 201 Created"),
    ACCEPTED_202("HTTP/1.1 202 Accepted"),
    NOTFOUND_404("HTTP/1.1 404 File Not Found"),
    NOTIMPLEMENTED_501("HTTP/1.1 501 Not Implemented"),
	BAD_REQUEST("HTTP/1.1 400: Bad Request "),
	TEAPOT("HTTP/1.1 418: I'm a teapot ");
	
	String header;

        StandardResponseHeader(String header){
            this.header=header;
        }

    public String getHeader() {
        return header;
    }
}
