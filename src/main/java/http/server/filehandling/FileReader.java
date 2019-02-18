package http.server.filehandling;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {

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
