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

    protected void verify(HttpServletRequest req, HttpServletResponse res) throws SQLException, ServletException, IOException{
        String uname = req.getParameter("uname");
        String pass= req.getParameter("pass");

        String sql = "SELECT username, password FROM users where username = ?";

        PrintWriter pw = res.getWriter();

        try(Connection con = regDAO.dbConnect();
            PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, uname);
                ResultSet rs = ps.executeQuery();

                if(rs.next() && rs.getString("password").equals(pass)){
                    RequestDispatcher dispatcher = req.getRequestDispatcher("Home.jsp");
                    dispatcher.forward(req, res);
                }
                else {    
                    pw.println("user not found or invalid credentials... ");
                }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

    } 
    
}
