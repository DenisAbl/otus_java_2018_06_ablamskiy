import dbservice.datasets.AddressDataSet;
import dbservice.datasets.UserDataSet;
import dbservice.dbservices.DBService;
import dbservice.dbservices.DBServiceHibernateImpl;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import webserver.servlets.AdminPageServlet;
import webserver.servlets.TemplateProcessor;
import webserver.servlets.UserPageServlet;

import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static final int PORT = 8080;
    private static final String HTML_PATH = "public_html";
    private static final String JETTY_AUTH_PROPERTIES = "src/main/resources/jetty_auth.properties";

    public static void main(String[] args) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(HTML_PATH);
        resourceHandler.setWelcomeFiles(new String[]{"adminPage"});

        DBService dbService = new DBServiceHibernateImpl();
        if (!dbService.existLogin("admin", UserDataSet.class)){
            dbService.save(new UserDataSet("admin","admin",0,new AddressDataSet("-"),new ArrayList<>(),"admin"));
        }
        TemplateProcessor templateProcessor = new TemplateProcessor();

        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS | ServletContextHandler.SECURITY);
        servletContext.addServlet(new ServletHolder(new AdminPageServlet(dbService, templateProcessor)),"/adminPage");
        servletContext.addServlet(new ServletHolder(new UserPageServlet(dbService, templateProcessor)),"/userPage");

        Server server = new Server(PORT);

        Constraint constraint = new Constraint();
        constraint.setName(Constraint.__FORM_AUTH);
        constraint.setRoles(new String[] {"admin"});
        constraint.setAuthenticate(true);

        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/*");
        mapping.setConstraint(constraint);

        LoginService loginService = new HashLoginService("custom",JETTY_AUTH_PROPERTIES);
        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        security.setConstraintMappings(Collections.singletonList(mapping));
        FormAuthenticator authenticator = new FormAuthenticator("/login.html", "/errorPage.html", false);

        security.setAuthenticator(authenticator);
        security.setLoginService(loginService);

        servletContext.setSecurityHandler(security);
        server.setHandler(new HandlerList(resourceHandler,servletContext));

        server.start();
        server.join();

    }
}
