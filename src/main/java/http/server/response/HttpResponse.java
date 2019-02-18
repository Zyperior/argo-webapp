package http.server.response;

import http.server.filehandling.FileReader;
import http.server.filehandling.KnownFileTypes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class HttpResponse {

    private String[] customHeader;
    private File file = null;
    private int fileLength;
    private boolean isHead;
    private String content = "text/plain";

    public HttpResponse(File file, boolean isHead, String... customHeader){
        this.customHeader = customHeader;
        this.isHead = isHead;
        if(file!=null){
            this.file = file;
            this.fileLength = (int)file.length();

            for (KnownFileTypes ftype : KnownFileTypes.values()) {
                if(file.getPath().endsWith(ftype.getSuffix())){
                    this.content = ftype.getContentType();

                }
            }
        }

    }

    public void sendResponse(PrintWriter out, BufferedOutputStream dataOut) {
        for(String headerLine : customHeader){
            out.println(headerLine);
        }
        if(file!=null){
            out.println("Server: ArgoWebapplication 1.0");
            out.println("Date: " + new Date());
            out.println("Content-type: " + content);
            out.println("Content-length: " + fileLength);

        }
        out.println(); // blank line between headers and content, very important !
        out.flush();
        if(!isHead){
            byte[] fileData;
            try {
                fileData = FileReader.readFileData(file);
                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }

        }

    }


}
