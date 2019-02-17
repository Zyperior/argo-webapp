package http.server.response;

import http.server.filehandling.FileReader;
import http.server.filehandling.FileRetriever;
import http.server.filehandling.KnownFileTypes;
import http.server.filehandling.StandardFileNames;
import http.server.serviceloader.PluginLoader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

abstract class HttpResponse {

    private File file;
    private int fileLength;
    boolean body = true;
    boolean fileNotFound = false;
    private String content = "text/plain";
    PrintWriter out;
    private BufferedOutputStream dataOut;

    HttpResponse(String fileRequested, PrintWriter out, BufferedOutputStream dataOut){
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

        if(params != null){
            Map<String, List<String>> paramList = null;
            try {
                URL url = new URL("http://?" + params);
                paramList = URLParsing.splitQuery(url);
            } catch (MalformedURLException e) {
                System.err.println("Error parsing URL params: " + e.getMessage());
            } catch (UnsupportedEncodingException e) {
                System.err.println("Error encoding URL params: " + e.getMessage());
            }

            if(paramList != null){

                String[] test = new String[1];
                for (String key : paramList.keySet()){
                    System.out.println(key + " " + paramList.get(key).get(0));
                    PluginLoader.run(key, paramList.get(key).get(0), test);
                }
            }


        }

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
