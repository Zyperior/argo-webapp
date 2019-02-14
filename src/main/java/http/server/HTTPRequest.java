package http.server;

import java.io.*;
import java.util.Date;

public class HTTPRequest {

    static final File WEB_ROOT = new File(".");
    static final String DEFAULT_FILE = "index.html";
    static final String FILE_NOT_FOUND = "404.html";
    static final String METHOD_NOT_SUPPORTED = "501.html";

    private File file;
    private int fileLength;
    private RequestType type;
    private String fileRequested;
    private String content;
    private PrintWriter out;
    private BufferedOutputStream dataOut;

    public HTTPRequest(RequestType type, String fileRequested, PrintWriter out, BufferedOutputStream dataOut) throws IOException {

        this.type = type;
        this.out = out;
        this.dataOut = dataOut;
        this.fileRequested = fileRequested;
        try{
            this.file = getRequestedFile();
        }
        catch(FileNotFoundException e){
            fileNotFound();
        }

        this.fileLength = (int)file.length();

        switch(type){
            case GET:
                get();
                break;
            case HEAD:
                head();
                break;
            case POST:
                post();
                break;
            case INVALID:
                invalid();
        }

    }

    private void get() throws IOException {
        out.println("HTTP/1.1 200 OK");
        standardOutToClient();
    }

    private void head(){

    }

    private void post(){

    }

    private void invalid() throws IOException {
        out.println("HTTP/1.1 501 Not Implemented");
        standardOutToClient();
    }

    private void fileNotFound() throws IOException {
        out.println("HTTP/1.1 404 File Not Found");
        standardOutToClient();

    }

    private void standardOutToClient() throws IOException {
        byte[] fileData = readFileData(fileLength);

        out.println("Server: Java HTTP Server from SSaurel : 1.0");
        out.println("Date: " + new Date());
        out.println("Content-type: " + content);
        out.println("Content-length: " + fileLength);
        out.println(); // blank line between headers and content, very important !
        out.flush(); // flush character output stream buffer
        dataOut.write(fileData, 0, fileLength);
        dataOut.flush();
    }

    private File getRequestedFile() throws FileNotFoundException{

        File file;

        if (fileRequested.endsWith("/")) {
            fileRequested += DEFAULT_FILE;
        }

        if(type==RequestType.INVALID){
            fileRequested = METHOD_NOT_SUPPORTED;
        }

        file = new File(WEB_ROOT, fileRequested);

        if(!file.exists()){
            fileRequested = FILE_NOT_FOUND;
        }

        file = new File(WEB_ROOT, fileRequested);

        content = getContentType(fileRequested);
        return file;

    }

    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html"))
            return "text/html";
        else
            return "text/plain";
    }

    private byte[] readFileData(int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null) {
                fileIn.close();
            }
        }

        return fileData;
    }
}
