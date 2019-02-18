package http.server.filehandling;

import java.io.File;

import static http.server.filehandling.StandardFileNames.*;

public class FileRetriever {

    private static final File WEB_ROOT = new File(".");

    public static File getRequestedFile(String fileRequested) {

        File file;

        if(fileRequested.equals("501")){
            fileRequested = BAD_REQUEST.getFileName();
        }

        if (fileRequested.endsWith("/")) {
            fileRequested += DEFAULT_FILE.getFileName();
        }

        file = new File(WEB_ROOT, fileRequested);

        if (!file.exists()) {
            fileRequested = FILE_NOT_FOUND.getFileName();
            file = new File(WEB_ROOT, fileRequested);
        }

        return file;

    }
}
