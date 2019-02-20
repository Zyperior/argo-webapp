package http.server.response;

public enum StandardResponseHeader {
    OK_200("HTTP/1.1 200 OK"),
    CREATED_201("HTTP/1.1 201 Created"),
    ACCEPTED_202("HTTP/1.1 202 Accepted"),
    NOTFOUND_404("HTTP/1.1 404 File Not Found"),
    NOTIMPLEMENTED_501("HTTP/1.1 501 Not Implemented");

        String header;

        StandardResponseHeader(String header){
            this.header=header;
        }

    public String getHeader() {
        return header;
    }
}
