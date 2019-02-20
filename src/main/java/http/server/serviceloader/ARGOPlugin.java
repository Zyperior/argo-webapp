package http.server.serviceloader;

import http.server.response.HttpResponse;
import java.util.List;
import java.util.Map;

public interface ARGOPlugin {
    HttpResponse doSomething(String doThis, Map<String, List<String>> params);
}
