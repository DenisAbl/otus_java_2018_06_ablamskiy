package webserver.servlets;

import dbservice.datasets.AddressDataSet;
import dbservice.datasets.PhoneDataSet;
import dbservice.datasets.UserDataSet;
import dbservice.dbservices.DBService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminPageServlet extends HttpServlet {

    private static final String ADMIN_PAGE_TEMPLATE = "adminPage.html";
    private final DBService dbService;
    private final TemplateProcessor templateProcessor;

    public AdminPageServlet(DBService dbService, TemplateProcessor templateProcessor){
        this.dbService = dbService;
        this.templateProcessor = templateProcessor;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession().getAttribute("login") != null) {
            Map<String, Object> pageVariables = createPageVariablesMap(dbService);
            String page = templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageVariables);
            response.getWriter().println(page);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        if (request.getSession().getAttribute("login") != null) {
            String name = request.getParameter("name");
            String login = request.getParameter("login");
            if (dbService.existLogin(login, UserDataSet.class)){
                response.getWriter().println("Login unavailable, please try another one");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            int age;
            try{
            age = Integer.parseInt(request.getParameter("age"));}
            catch(NumberFormatException e){
                response.getWriter().println("Incorrect age format");
                return;
            }
            AddressDataSet address = new AddressDataSet(request.getParameter("address"));
            List<PhoneDataSet> phone = new ArrayList<>();
            phone.add(new PhoneDataSet(request.getParameter("phone")));
            dbService.save(new UserDataSet(login, name, age, address,phone));
            response.getWriter().println("User added");
            response.setStatus(HttpServletResponse.SC_OK);}
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Unauthorized access");
        }
    }

    private static Map<String, Object> createPageVariablesMap(DBService service) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("usersNumber", service.getUsersNumber(UserDataSet.class));

        return pageVariables;
    }

    private void saveToSession(HttpServletRequest request, String login){
        request.getSession().setAttribute("login", login);
    }

    private void validateUser(HttpServletRequest request){
//        long loginId = Long.parseLong(request.getParameter("login"));
//        if (loginId.equals(dbService.load(loginId,UserDataSet.class).)){
//            saveToSession(request,loginId);
        }

}