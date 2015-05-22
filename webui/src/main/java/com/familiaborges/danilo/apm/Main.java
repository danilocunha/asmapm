package com.familiaborges.danilo.apm;

import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import com.familiaborges.danilo.apm.webui.MyVaadinUI;

public class Main {

	public static void main(String[] args) throws Exception {
		final int port = Integer.parseInt(System.getProperty("port", "8080"));
		final Server server = new Server(port);
		/*ServletContextHandler context = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		context.setContextPath("/");*/
		
		WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        
        webapp.setServer(server);
        //webapp.addEventListener(new WebUIServletContextListener());
        //webapp.setResourceBase(".");
        webapp.setParentLoaderPriority(true);
		//ServletContextHandler servletHandler = new ServletContextHandler();
		// servletHandler.addFilter(GuiceFilter.class, "/*",
		// EnumSet.of(DispatcherType.REQUEST));
		//servletHandler.addEventListener(new WebUIServletContextListener());
        ProtectionDomain protectionDomain = Main.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
        webapp.addServlet(MyVaadinUI.Servlet.class, "/*");
        System.out.println("Location:" + location.toExternalForm());
        
        webapp.setResourceBase(location.toExternalForm());
        webapp.setWar(location.toExternalForm());
        
		server.setHandler(webapp);
		server.start();
		server.join();
		
		

	}

}
