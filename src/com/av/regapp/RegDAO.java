package com.av.regapp;


import java.sql.*;

public class RegDAO{
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
    protected Connection dbConnection;


    RegDAO(String jdbcUrl, String jdbcUsername, String jdbcPassword){
        this.jdbcUrl = jdbcUrl;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    // Connect Database
    protected void dbConnect() throws SQLException{
        if(dbConnection == null || dbConnection.isClosed()){

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (Exception e) {
                throw new SQLException(e);
            }

            dbConnection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);

        }
    }

    // Disconnect Database
    protected void dbDisconnect() throws SQLException{
        if(dbConnection != null && !dbConnection.isClosed()){
            dbConnection.close();
        }
    }



}