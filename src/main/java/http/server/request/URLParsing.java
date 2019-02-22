package http.server.request;


import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * Single-method class for parsing parameters from requests to be interpreted in plugin modules.
 *
 * Created by Unknown, found on StackOverflow
 * <a href="https://stackoverflow.com/questions/13592236/parse-a-uri-string-into-name-value-collection">Source</a>
 */
class URLParsing {

    /**
     * Splits the parameters from the url and puts them in a map.
     * Can handle null-values and multiple values.
     * @param url URL - Complete URL with parameters
     * @return Map<String, List<String>>
     */
    static Map<String, List<String>> splitQuery(URL url) {


        final Map<String, List<String>> query_pairs = new LinkedHashMap<>();
        final String[] pairs = url.getQuery().split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8) : pair;
            if (!query_pairs.containsKey(key)) {
                query_pairs.put(key, new LinkedList<>());
            }
            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8) : null;
            query_pairs.get(key).add(value);
        }

        return query_pairs;

    }
}
