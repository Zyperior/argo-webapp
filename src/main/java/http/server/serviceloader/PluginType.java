package http.server.serviceloader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to be used by plugins for the Service-loader to identify the correct plugin from request.
 *
 * Created by Andreas Albihn, 2019-02-17 based on the code from lecture
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PluginType {
    String value() default  "/default";
}
