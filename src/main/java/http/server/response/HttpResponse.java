package http.server.response;

import http.server.filehandling.FileReader;
import http.server.filehandling.FileRetriever;
import http.server.filehandling.KnownFileTypes;
import http.server.filehandling.StandardFileNames;

import java.io.*;
import java.util.Date;

abstract class HttpResponse {

    private File file;
    private int fileLength;
    boolean body = true;
    boolean fileNotFound = false;
    private String content = "text/plain";
    PrintWriter out;
    private BufferedOutputStream dataOut;

    HttpResponse(String fileRequested, PrintWriter out, BufferedOutputStream dataOut) throws IOException {
        this.dataOut = dataOut;
        this.out = out;

        // check and retrieve params if they exist
        String params = null;
        try {
            if (fileRequested.contains("?")) {
                String[] parts = fileRequested.split("\\?");

                fileRequested = parts[0];
                params = parts[1];
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error retrieving params: " + e.getMessage());
        }

        System.out.println(fileRequested);
        System.out.println(params);

        this.file = FileRetriever.getRequestedFile(fileRequested);

        if(file.getPath().equals(StandardFileNames.FILE_NOT_FOUND.getFileName())){
            fileNotFound = true;
        }

        this.fileLength = (int) file.length();

        //Check if filerequested is a static file of known type
        for (KnownFileTypes ftype: KnownFileTypes.values()) {
            if(fileRequested.endsWith(ftype.getSuffix())){
                content = ftype.getContentType();
            }
        }
    }

    void fileNotFound() throws IOException {
        out.println("HTTP/1.1 404 File Not Found");
        standardOutToClient();
    }

    void standardOutToClient() throws IOException {
        byte[] fileData = FileReader.readFileData(file);

        out.println("Server: Java HTTP Server from SSaurel : 1.0");
        out.println("Date: " + new Date());
        out.println("Content-type: " + content);
        out.println("Content-length: " + fileLength);
        out.println(); // blank line between headers and content, very important !
        out.flush(); // flush character output stream buffer
        if(body){
            dataOut.write(fileData, 0, fileLength);
        }
        dataOut.flush();
    }


    abstract void sendResponse() throws IOException;
}
