package http.server.serviceloader;

import http.server.response.HttpResponse;
import java.util.List;
import java.util.Map;

/**
 * Common interface for all plugins to be run by the Plugin-loader.
 *
 * Created by Andreas Albihn, 2019-02-17 based on the code from lecture
 */
public interface ARGOPlugin {
    HttpResponse doSomething(String doThis, Map<String, List<String>> params);
}
