package main.java.com.av.regapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import main.java.com.av.regapp.services.RegisterationServices;

public class RegisterationController extends HttpServlet{
   RegisterationServices regServices;
   private static final Logger log = LogManager.getLogger(RegisterationController.class);

    @Override
    public void init() throws ServletException {
        // getting and parsing application.yml file
        String source = getServletContext().getInitParameter("configuration");
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(source);
        Yaml yml = new Yaml();
        Map<String, Object> config = yml.load(input);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> datasource = (Map<String, Object>) config.get("datasource");

        String jdbcUrl = (String) datasource.get("url");
        String jdbcUsername = (String) datasource.get("username");
        String jdbcPassword = (String) datasource.get("password");
        regServices = new RegisterationServices(jdbcUrl, jdbcUsername, jdbcPassword);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = request.getServletPath();

        try{
            switch (path) {
                case "/registerForm":
                    showRegisterForm(request, response);
                    break;

                case "/login":
                    showLogin(request, response);
                    break;                   

                case "/home":
                    showHomePage(request, response);
                    break;
                
                case "/verify":
                    regServices.loginVerify(request, response);
                    break;

                case "/register":
                    regServices.register(request, response);
                    break;

                case "/logout":
                    regServices.logout(request, response);
                    break;
                
                default:
                    showLogin(request, response);
                    break;
            }
        } catch (Exception e){
            log.error("Got error in controller: {}", e);
            throw new ServletException(e);
        }
    }

    private void showLogin(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        RequestDispatcher dispatcher = req.getRequestDispatcher("Login.jsp");
        dispatcher.forward(req, res);
    }

    private void showRegisterForm(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        RequestDispatcher dispatcher = req.getRequestDispatcher("Register.jsp");
        dispatcher.forward(req, res);
    }

    private void showHomePage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        RequestDispatcher dispatcher = req.getRequestDispatcher("Home.jsp");
        dispatcher.forward(req, res);
    }
}
