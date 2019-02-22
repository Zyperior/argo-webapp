package http.server.filehandling;

/**
 * Enum for setting the file paths for the server default files.
 *
 * Created by Andreas Albihn, 2019-02-14
 */
public enum StandardFileNames {
    DEFAULT_FILE("./files/html/index.html"),
    FILE_NOT_FOUND("./files/html/404.html"),
    BAD_REQUEST("./files/html/501.html");

    String fileName;

    StandardFileNames(String name){
        this.fileName = name;
    }

    public String getFileName() {
        return fileName;
    }
}
