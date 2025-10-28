package main.java.com.av.regapp.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.av.regapp.dao.RegisterationDAO;
import main.java.com.av.regapp.model.Registeration;
import main.java.com.av.regapp.utils.SHAHasing;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

public class RegisterationServices extends HttpServlet{
    RegisterationDAO regDAO;
    private static final Logger log = LogManager.getLogger(RegisterationServices.class);

    public RegisterationServices(String jdbcUrl, String jdbcUsername, String jdbcPassword){
        regDAO = new RegisterationDAO(jdbcUrl, jdbcUsername, jdbcPassword);
    }

    // Login Verification
    public void loginVerify(HttpServletRequest req, HttpServletResponse res) throws SQLException, ServletException, IOException{
        String uname = req.getParameter("uname");
        String pass= req.getParameter("pass");
        HttpSession session = req.getSession();

        PrintWriter pw = res.getWriter();

        if(regDAO.getData(uname, pass)){
            session.setAttribute("currentUserName", uname);
            log.info("login successfull.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("Home.jsp");
            dispatcher.forward(req, res);
           
        } else {    
            pw.println("Invalid credentials... ");
            log.debug("Invalid credentials: {}", uname);
        }
    } 

    // user Registeration
    public void register(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");

        String username = req.getParameter("uname");
        String password = req.getParameter("pass");
        String email = req.getParameter("email");
        String salt = SHAHasing.getSalt();
        String hashPassword = SHAHasing.hash(password, salt);

        Registeration reg = new Registeration(username, email, salt, hashPassword);

        PrintWriter pw = res.getWriter();

        if(regDAO.save(reg)){
            log.info("Data inserted successfully....");
            pw.println("<p> Registered successfully. <a href='/login'>Click</a> here to login.");
            
        }
        else{
            log.debug("Registeration failed.");
            pw.println("Unable to Register.");
        }
    }

    // Logout
    public void logout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        HttpSession session = req.getSession(false);
        if(session != null && session.getAttribute("currentUserName") != null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("Login.jsp");
            log.debug("session out: {}", session);
            session.invalidate();
            dispatcher.forward(req, res);
           
        }
    }
    
}
