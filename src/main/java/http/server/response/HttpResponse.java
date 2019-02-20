package http.server.response;

import http.server.filehandling.FileReader;
import http.server.filehandling.KnownFileTypes;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Http-response class for creating and sending responses to clients using HTTP-protocol.
 * <p>Custom responses can be created or use standard known response types
 * @see StandardResponseHeader </p>
 *
 * @author name: Andreas Albihn
 */
public class HttpResponse {

    private String[] customHeader;
    private int fileLength;
    private boolean isHead;
    private String content = "text/plain";
    private byte[] bodyData;

    /**
     * Constructs a head-response to the client, no body
     *
     * @param customHeader your header response, will print in order
     */
    public HttpResponse(String... customHeader){
        this.isHead = true;
        this.customHeader =  customHeader;
    }

    /**
     * Constructs a head-response to the client, no body
     *
     * @param header your header response of the known default response type
     */
    public HttpResponse(StandardResponseHeader header){
        this.isHead = true;
        this.customHeader = new String[]{header.getHeader()};
    }


    private HttpResponse(boolean isHead, String... customHeader){
        this.isHead = isHead;
        this.customHeader = customHeader;
    }

    /**
     * Constructs a response to the client
     *
     * @param file the file you want to be printed as bytes in the response body
     * @param customHeader your header response, will print in order
     */
    public HttpResponse(@NotNull File file, String... customHeader){
        this(false,customHeader);
        try {
            this.bodyData = FileReader.readFileData(file);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        for (KnownFileTypes ftype : KnownFileTypes.values()) {
            if(file.getPath().endsWith(ftype.getSuffix())){
                this.content = ftype.getContentType();

            }
        }
        this.fileLength = bodyData.length;

    }

    /**
     * Constructs a response to the client
     *
     * @param file the file you want to be printed as bytes in the response body
     * @param header your header response of the known default response types
     */
    public HttpResponse(@NotNull File file, StandardResponseHeader header){
        this(file, header.getHeader());

    }

    /**
     * Constructs a response to the client
     *
     * @param bodyData will be printed as bytes in the response body
     * @param fileType will set the content of the response to the known types, null/default = "text/plain"
     * @param customHeader your header response, will print in order
     */
    public HttpResponse(@NotNull byte[] bodyData,KnownFileTypes fileType, String... customHeader){
        this(false, customHeader);
        this.bodyData = bodyData;
        if(fileType!=null){
            this.content = fileType.getContentType();
        }
        this.fileLength = bodyData.length;

    }

    /**
     * Constructs a response to the client
     *
     * @param bodyData will be printed as bytes in the response body
     * @param fileType will set the content of the response to the known types, null/default = "text/plain"
     * @param header your header response of the known default response types
     */
    public HttpResponse(@NotNull byte[] bodyData,KnownFileTypes fileType, StandardResponseHeader header){
        this(bodyData, fileType, header.getHeader());
    }


    /**
     * Prints the respons to the selected outputs
     *
     * @param out printwriter for headers
     * @param dataOut buffered output stream for printing bytes to the body
     */
    public void sendResponse(PrintWriter out, BufferedOutputStream dataOut) {
        for(String headerLine : customHeader){
            out.println(headerLine);
        }
        out.println("Server: ArgoWebapplication 1.0");
        out.println("Date: " + new Date());
        out.println("Content-type: " + content);
        out.println("Content-length: " + fileLength);
        out.println(); // blank line between headers and content, very important !
        out.flush();
        if(!isHead){
            try {
                dataOut.write(bodyData, 0, fileLength);
                dataOut.flush();
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        }



    }


}
