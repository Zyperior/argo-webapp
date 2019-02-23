package http.server.filehandling;


/**
 * Enum for setting the current supported file-types of the server.
 * Used for retrieving the correct content-type in the HTTP-Response
 *
 * Created by Andreas Albihn, 2019-02-14
 */
public enum KnownFileTypes {
    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    JAVASCRIPT(".js", "text/javascript"),
    PDF("pdf", "application/pdf"),
    JPEG(".jpeg", "image/jpeg"),
    JPG(".jpg", "image/jpg"),
    PNG(".png", "image/png"),
    SVG(".svg", "image/svg"),
    JSON(".json","application/json ; charset=utf-8"),
    DEFAULT("/", "text/html");

    private String suffix;
    private String contentType;

    KnownFileTypes(String value, String contentType){
        this.suffix = value;
        this.contentType = contentType;
    }

    public String getSuffix(){
        return suffix;
    }

    public String getContentType(){
        return contentType;
    }

}
