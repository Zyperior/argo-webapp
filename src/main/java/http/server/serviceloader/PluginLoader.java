package http.server.serviceloader;

import http.server.response.HttpResponse;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Class for retrieving the plugins and running them with the selected parameters from the client request.
 *
 * Created by Andreas Albihn, 2019-02-17 based on the code from lecture
 */
public class PluginLoader {

    /**
     * Creates a URLClassLoader object containing a list of the .jar files in the specified plugin-folder
     * @return URLClassLoader
     */
    private static URLClassLoader createClassLoader(){
        File loc = new File("./plugins/v1");
        File[] fileList = loc.listFiles(fle -> fle.getPath().toLowerCase().endsWith(".jar"));
        URL[] urls = new URL[0];
        if (fileList != null) {
            urls = new URL[fileList.length];
            for (int i = 0; i < fileList.length; i++) {
                try {
                    urls[i] = fileList[i].toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

        return new URLClassLoader(urls);
    }

    /**
     * Checks the URLClassLoader list and runs the corresponding class annotated with a PluginType annotation.
     * Returns a HttpResponse from the plugin to the server.
     *
     * @param thisPlugin String - must match value of PluginType-annotation in plugin.
     * @param doThis String - not used.
     * @param paramList Map - Keys and values sent by the client to be used in the plugin.
     * @return HttpResponse
     */
    public static HttpResponse run(String thisPlugin, String doThis, Map<String, List<String>> paramList) {
        URLClassLoader ucl = createClassLoader();

        ServiceLoader<ARGOPlugin> loader = ServiceLoader.load(ARGOPlugin.class, ucl);

        for (ARGOPlugin plugin : loader) {
            if(plugin.getClass().getAnnotation(PluginType.class).value().equals(thisPlugin)){
               return plugin.doSomething(doThis,paramList);
            }
        }

        return null;
    }
}
