package http.server.serviceloader;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ServiceLoader;

public class PluginLoader {

    private URLClassLoader createClassLoader(){
        File loc = new File("/plugins/");
        File[] fileList = loc.listFiles(new FileFilter() {
            public boolean accept(File fle) {return fle.getPath().toLowerCase().endsWith(".jar");}
        });
        URL[] urls = new URL[fileList.length];
        for (int i = 0; i < fileList.length; i++) {
            try {
                urls[i] = fileList[i].toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return new URLClassLoader(urls);
    }

    private void run(String thisPlugin, String doThis, String[] params) {
        URLClassLoader ucl = createClassLoader();

        ServiceLoader<ARGOPlugin> loader = ServiceLoader.load(ARGOPlugin.class, ucl);

        for (ARGOPlugin ARGOPlugin : loader) {
            ARGOPlugin.doSomething(doThis,params);
        }

    }
}
