package http.server.filehandling;

public enum StandardFileNames {
    DEFAULT_FILE("index.html"),
    FILE_NOT_FOUND("404.html"),
    BAD_REQUEST("501.html");

    String fileName;

    StandardFileNames(String name){
        this.fileName = name;
    }

    public String getFileName() {
        return fileName;
    }
}
