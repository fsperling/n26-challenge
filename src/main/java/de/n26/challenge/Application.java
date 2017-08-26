package de.n26.challenge;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 *
 * @author felix
 */
public class Application {
    public static void main(String[] args) throws Exception {
        String contextPath = "";
        String appBase = ".";

        Tomcat tomcat = new Tomcat();
        tomcat.getHost().setAppBase(appBase);
        Context context = tomcat.addWebapp(contextPath, appBase);

        Tomcat.addServlet(context, "jersey-container-servlet",
                new ServletContainer(resourceConfig()));
        context.addServletMapping("/api/*", "jersey-container-servlet");

        tomcat.start();
        tomcat.getServer().await();
    }

    private static ResourceConfig resourceConfig() {
        return new JerseyConfiguration();
    }    
}
