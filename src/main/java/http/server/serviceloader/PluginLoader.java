package http.server.serviceloader;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ServiceLoader;

public class PluginLoader {

    private static URLClassLoader createClassLoader(){
        File loc = new File("./plugins");
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

    public static void run(String thisPlugin, String doThis, String[] params) {
        URLClassLoader ucl = createClassLoader();

        ServiceLoader<ARGOPlugin> loader = ServiceLoader.load(ARGOPlugin.class, ucl);

        /**
         * Code executes well until this loop. It just skips the loop all together as if there is
         * nothing in the loader??
         */
        for (ARGOPlugin plugin : loader) {
            if(plugin.getClass().getAnnotation(PluginType.class).value().equals(thisPlugin)){
                plugin.doSomething(doThis,params);
            }
        }

    }
}
