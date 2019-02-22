package http.server.request;

import http.server.filehandling.FileRetriever;
import http.server.filehandling.KnownFileTypes;
import http.server.filehandling.StandardFileNames;
import http.server.response.HttpResponse;
import http.server.response.StandardResponseHeader;
import http.server.serviceloader.PluginLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpRequest {

    private RequestType type;
    private String requestedURL;
    private String params;
    private Map<String, List<String>> paramList = null;
    private File file;
    private boolean fileNotFound = false;

    public HttpRequest(RequestParser parsedRequest){

        this.type = parsedRequest.getRequestType();
        this.requestedURL = parsedRequest.getFileRequested();

        if(type==RequestType.GET){
            parseURL(parsedRequest.getFileRequested());
            setParamList(params);
        }
        else if(type==RequestType.POST){
            parseURL(parsedRequest.getFileRequested()+"?"+parsedRequest.getParams());
            setParamList(params);
        }

    }


    public RequestType getType() {
        return type;
    }

    public String getRequestedURL() {
        return requestedURL;
    }

    public String getParams() {
        return params;
    }

    public Map<String, List<String>> getParamList() {
        return paramList;
    }

    /**
     * @param params string of parameters from request.
     * Sets a map of the current parameters with the name as key and its values.
     */
    private void setParamList(String params){
        try {
            URL url = new URL("http://?" + params);
            this.paramList =  URLParsing.splitQuery(url);
        } catch (MalformedURLException e) {
            System.err.println("Error parsing URL params: " + e.getMessage());
        }

    }

    /**
     * @param url = URL Request sent by client
     * Splits the incoming requested URL in to URL and parameters
     */
    private void parseURL(String url){
        try {
            if (url.contains("?")) {
                String[] parts = url.split("\\?");

                this.requestedURL = parts[0];
                this.params = parts[1];
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error retrieving params: " + e.getMessage());
        }
    }

    public HttpResponse processRequest(){
        HttpResponse httpResponse;
        boolean getFile = isFileRequested(requestedURL);
        if(getFile){

            //Do something with params..?

            httpResponse = new HttpResponse(file, StandardResponseHeader.OK_200);

            if(fileNotFound){
                this.file = FileRetriever.getRequestedFile("./files/html/404.html");
                httpResponse = new HttpResponse(file,StandardResponseHeader.NOTFOUND_404);
            }

        }
        else{
            httpResponse = PluginLoader.run(requestedURL,"test", paramList);
        }
        if(httpResponse==null){
            this.file = FileRetriever.getRequestedFile("./files/html/501.html");
            httpResponse = new HttpResponse(file, StandardResponseHeader.NOTIMPLEMENTED_501);
        }

        return httpResponse;

    }

    private boolean isFileRequested(String url){
        for (KnownFileTypes ftype : KnownFileTypes.values()) {
            if(url.endsWith(ftype.getSuffix())){

                this.file = FileRetriever.getRequestedFile(requestedURL);

                if(file.getPath().equals(StandardFileNames.FILE_NOT_FOUND.getFileName())){
                    fileNotFound = true;
                }

                return true;
            }
        }

        return false;
    }

}
