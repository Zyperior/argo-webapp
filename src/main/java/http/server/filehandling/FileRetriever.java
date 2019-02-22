package http.server.filehandling;

import java.io.File;

import static http.server.filehandling.StandardFileNames.*;

/**
 * Single-method class for retrieving the requested file.
 *
 * Created by Andreas Albihn, 2019-02-14
 */
public class FileRetriever {

    private static final File WEB_ROOT = new File(".");

    /**
     * @param fileRequested String - filepath from server root.
     *                      If it ends with "/", will be set to the default index.html path.
     *                      If not found, will be set to the default file not found 404.html path.
     *
     * @return File
     */
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
