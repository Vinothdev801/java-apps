package com.av.regapp;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegController extends HttpServlet{

    private RegServices regServices = new RegServices();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = request.getServletPath();

        try{
            switch (path) {
                case "/register":
                    showRegisterForm(request, response);
                    break;

                case "/login":
                    showLogin(request, response);
                    break;                   

                // case "/home":
                //     showHomePage(request, response);
                //     break;
                
                case "/verify":
                    regServices.verify(request, response);
                    break;
                
                default:
                    showErrorPage(request, response);
                    break;
            }
        } catch (Exception e){
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

    private void showErrorPage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        RequestDispatcher dispatcher = req.getRequestDispatcher("Error.jsp");
        dispatcher.forward(req, res);
    }

    // private void showHomePage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
    //     RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
    //     dispatcher.forward(req, res);
    // }
}
