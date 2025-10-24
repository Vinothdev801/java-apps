package com.av.regapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

public class RegServices extends HttpServlet{
    RegDAO regDAO;
    Connection connection;

    public void init(){
        String jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        regDAO = new RegDAO(jdbcUrl, jdbcUsername, jdbcPassword);
        connection = regDAO.dbConnection;
    }

    protected void verify(HttpServletRequest req, HttpServletResponse res) throws SQLException, ServletException, IOException{
        String username = req.getParameter("uname");
        String password = req.getParameter("pass");

        regDAO.dbConnect();

        String sql = "SELECT user_name, password FROM users";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            if(resultSet.getString("user_name") == username && password == resultSet.getString("password")){
                RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
                dispatcher.forward(req, res);
                break; // loop break.
            }
        }

        resultSet.close();
        statement.close();
        connection.close();
    } 
    
}
