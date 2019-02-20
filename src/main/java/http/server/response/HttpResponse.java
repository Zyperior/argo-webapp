package http.server.response;

import com.google.gson.JsonObject;
import http.server.filehandling.FileReader;
import http.server.filehandling.KnownFileTypes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

public class HttpResponse {

    private String[] customHeader;
    private File file = null;
    private int fileLength;
    private boolean isHead;
    private String content = "text/plain";
    private byte[] bodyData;
    private KnownFileTypes fileType;

    public HttpResponse(File file, byte[] body, boolean isHead, KnownFileTypes fileType, String... customHeader){
        this.customHeader = customHeader;
        this.isHead = isHead;
        this.bodyData = body;
        this.fileType = fileType;
        if(file!=null){
            this.file = file;
        }


    }

    public void sendResponse(PrintWriter out, BufferedOutputStream dataOut) {
        for(String headerLine : customHeader){
            out.println(headerLine);
        }
        if(bodyData!=null && fileType!= null){
            fileLength = bodyData.length;
            content = fileType.getContentType();
        }
        if(file!=null || bodyData!=null){

            if(fileType==null){
                this.fileLength = (int)file.length();

                for (KnownFileTypes ftype : KnownFileTypes.values()) {
                    if(file.getPath().endsWith(ftype.getSuffix())){
                        this.content = ftype.getContentType();

                    }
                }
            }

            out.println("Server: ArgoWebapplication 1.0");
            out.println("Date: " + new Date());
            out.println("Content-type: " + content);
            out.println("Content-length: " + fileLength);

        }
        out.println(); // blank line between headers and content, very important !
        out.flush();
        if(!isHead){


            byte[] fileData;
            if(file!=null){
                try {
                    fileData = FileReader.readFileData(file);
                    dataOut.write(fileData, 0, fileLength);
                    dataOut.flush();
                } catch (IOException e) {
                    System.err.println("Error reading file: " + e.getMessage());
                }
            }
            else if(bodyData!=null){
                try {
                    fileData = bodyData;
                    dataOut.write(fileData);
                    dataOut.flush();
                } catch (IOException e) {
                    System.err.println("Error reading data: " + e.getMessage());
                }
            }


        }



    }


}
