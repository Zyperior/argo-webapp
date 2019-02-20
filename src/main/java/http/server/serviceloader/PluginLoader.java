package http.server.serviceloader;

import http.server.response.HttpResponse;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class PluginLoader {

    private static URLClassLoader createClassLoader(){
        File loc = new File("./plugins");
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
