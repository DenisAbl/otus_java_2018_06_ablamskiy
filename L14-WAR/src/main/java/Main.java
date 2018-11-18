import dbservice.datasets.AddressDataSet;
import dbservice.datasets.UserDataSet;
import dbservice.dbservices.DBService;
import dbservice.dbservices.DBServiceHibernateImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import webserver.servlets.AdminPageServlet;
import webserver.servlets.TemplateProcessor;
import webserver.servlets.UserPageServlet;

import java.util.ArrayList;

public class Main {

    private static final int PORT = 8080;
    private static final String HTML_PATH = "public_html";

    public static void main(String[] args) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(HTML_PATH);
        resourceHandler.setWelcomeFiles(new String[]{"login.html"});

        DBService dbService = new DBServiceHibernateImpl();
        dbService.save(new UserDataSet("admin","admin",0,new AddressDataSet("-"),new ArrayList<>()));
        TemplateProcessor templateProcessor = new TemplateProcessor();

        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContext.addServlet(new ServletHolder(new AdminPageServlet(dbService,templateProcessor)),"/adminPage");
        servletContext.addServlet(new ServletHolder(new UserPageServlet(dbService,templateProcessor)),"/userPage");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler,servletContext));
        server.start();
        server.join();

    }
}
