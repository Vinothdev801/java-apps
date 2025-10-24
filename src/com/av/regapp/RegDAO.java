package com.av.regapp;


import java.sql.*;

public class RegDAO{
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection dbConnection;


    RegDAO(String jdbcUrl, String jdbcUsername, String jdbcPassword){
        this.jdbcUrl = jdbcUrl;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    // Connect Database
    protected Connection dbConnect() throws SQLException{
        if(dbConnection == null || dbConnection.isClosed()){

            try {
                Class.forName("com.mysql.jdbc.Driver");
                dbConnection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);

                System.out.println("DB connection successful!");
            } catch (Exception e) {
                throw new SQLException(e);
            }

            

        }
        return dbConnection;
    }

    // Disconnect Database
    protected void dbDisconnect() throws SQLException{
        if(dbConnection != null && !dbConnection.isClosed()){
            dbConnection.close();
        }
    }



}