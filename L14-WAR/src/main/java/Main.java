public class Main {

    private static final int PORT = 8080;
    private static final String HTML_PATH = "templates";
    private static final String JETTY_AUTH_PROPERTIES = "src/main/resources/jetty_auth.properties";

    public static void main(String[] args) throws Exception {
//       ResourceHandler resourceHandler = new ResourceHandler();
//        resourceHandler.setResourceBase(HTML_PATH);
//        resourceHandler.setWelcomeFiles(new String[]{"adminPage.html"});
//
//        DBService dbService = new DBServiceHibernateImpl();
//        if (!dbService.existLogin("admin",UserDataSet.class)){
//            dbService.save(new UserDataSet("admin","admin",0,new AddressDataSet("-"),new ArrayList<>(),"admin"));
//        }
//        TemplateProcessor templateProcessor = new TemplateProcessor();
//
//        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS | ServletContextHandler.SECURITY);
//        servletContext.addServlet(new ServletHolder(new AdminPageServlet()),"/adminPage");
//        servletContext.addServlet(new ServletHolder(new UserPageServlet()),"/userPage");
//
//        Server server = new Server(PORT);
//        /*
//        LoginService loginService = new HashLoginService("custom",JETTY_AUTH_PROPERTIES);
//
//        Constraint constraint = new Constraint();
//        constraint.setName(Constraint.__FORM_AUTH);
//        constraint.setRoles(new String[] {"admin"});
//        constraint.setAuthenticate(true);
//
//        ConstraintMapping mapping = new ConstraintMapping();
//        mapping.setPathSpec("/*");
//        mapping.setConstraint(constraint);
//
//        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
//        security.setConstraintMappings(Collections.singletonList(mapping));
//        FormAuthenticator authenticator = new FormAuthenticator("/login.html", "/errorPage.html", false);
//        security.setAuthenticator(authenticator);
//        security.setLoginService(loginService);
//
//        servletContext.setSecurityHandler(security);*/
//        server.setHandler(new HandlerList(resourceHandler,servletContext));
//
//
//        server.start();
//        server.join();

//        mvn package -DskipTests
//"C:\Program Files\Java\jdk-10.0.1\bin\java.exe" -Dmaven.multiModuleProjectDirectory=D:\OTUS\practice\L14-WAR "-Dmaven.home=C:\Program Files\maven" "-Dclassworlds.conf=C:\Pro
//gram Files\maven\bin\m2.conf" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2018.2.1\lib\idea_rt.jar=51930:C:\Program Files\JetBrains\IntelliJ IDEA 2018.2.1\bin" -Dfile.encoding=UTF-8 -classp
//ath "C:\Program Files\maven\boot\plexus-classworlds-2.5.2.jar" org.codehaus.classworlds.Launcher -Didea.version=2018.2.1 package -DskipTests
    }
}
