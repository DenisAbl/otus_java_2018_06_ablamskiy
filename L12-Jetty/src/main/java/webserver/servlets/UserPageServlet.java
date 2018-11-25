package webserver.servlets;

import dbservice.datasets.UserDataSet;
import dbservice.dbservices.DBService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserPageServlet extends HttpServlet {

    private DBService dbService;
    private TemplateProcessor templateProcessor;

    public UserPageServlet(DBService dbService, TemplateProcessor templateProcessor){
        this.dbService = dbService;
        this.templateProcessor = templateProcessor;
    }

    private static final String USER_PAGE_TEMPLATE = "userPage.html";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(request, response, dbService);
        if (pageVariables != null){
        String page = templateProcessor.getPage(USER_PAGE_TEMPLATE, pageVariables);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);}
        else response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request, HttpServletResponse response, DBService service) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        UserDataSet user;
        try{
        long id = Long.parseLong(request.getParameter("id"));
        user = service.load(id, UserDataSet.class);}
        catch  (NumberFormatException | NullPointerException e){
            response.getWriter().println("Incorrect id");
            return null;
        }

        pageVariables.put("name", user.getName());
        pageVariables.put("age", user.getAge());
        pageVariables.put("address", user.getAddress().getStreet());
        pageVariables.put("phones", user.getPhoneNumber().toString());
        pageVariables.put("login",user.getLogin());

        return pageVariables;
    }

}
