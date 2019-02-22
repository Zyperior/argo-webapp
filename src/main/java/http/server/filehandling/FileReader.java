package http.server.filehandling;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Single-method class for reading files and returning them as an array of bytes.
 *
 * Created by Andreas Albihn, 2019-02-14
 */
public class FileReader {

    /**
     *
     * @param file File-object to be converted to byte-array
     * @return byte[]
     * @throws IOException if file-object is unreadable
     */
    public static byte[] readFileData(File file) throws IOException {

        int fileLength = (int) file.length();

        byte[] fileData = new byte[fileLength];
        try (FileInputStream fileIn = new FileInputStream(file)) {
            //noinspection ResultOfMethodCallIgnored
            fileIn.read(fileData);
        }

        return fileData;
    }
}
