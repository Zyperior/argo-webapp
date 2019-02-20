package http.server.filehandling;

public enum KnownFileTypes {
    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    JAVASCRIPT(".js", "text/javascript"),
    PDF("pdf", "application/pdf"),
    JPEG(".jpeg", "image/jpeg"),
    PNG(".png", "image/png"),
    JSON(".json","application/json"),
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
