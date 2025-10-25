package com.av.regapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

public class RegServices extends HttpServlet{
    RegDAO regDAO;

    public RegServices(String jdbcUrl, String jdbcUsername, String jdbcPassword){
        regDAO = new RegDAO(jdbcUrl, jdbcUsername, jdbcPassword);
    }

    // Login Verification
    protected void loginVerify(HttpServletRequest req, HttpServletResponse res) throws SQLException, ServletException, IOException{
        String uname = req.getParameter("uname");
        String pass= req.getParameter("pass");

        PrintWriter pw = res.getWriter();

        if(regDAO.getData(uname, pass)){
            RequestDispatcher dispatcher = req.getRequestDispatcher("Home.jsp");
            dispatcher.forward(req, res);
        } else {    
            pw.println("Invalid credentials... ");
        }
    } 

    // user Registeration
    protected void register(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String username = req.getParameter("uname");
        String password = req.getParameter("pass");
        String email = req.getParameter("email");
        String salt = SHAHasing.getSalt();
        String hashPassword = SHAHasing.hash(password, salt);

        Reg reg = new Reg(username, email, salt, hashPassword);

        PrintWriter pw = res.getWriter();

        if(regDAO.save(reg)){
            System.out.println("Data inserted successfully....");
            pw.println("Registered Successfully");
        }
        else{
            pw.println("Unable to Register.");
        }
    }
    
}
