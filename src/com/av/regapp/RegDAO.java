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

    // get data from DB
    protected boolean getData(String uname, String pass){
        String sql = "SELECT * FROM users where username = ?";

        try(Connection con = dbConnect();
            PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setString(1, uname);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                String hash = SHAHasing.hash(pass, rs.getString("salt"));

                return rs.getString("password").equals(hash);
            }
            
        } catch (Exception e) {
            System.out.println("Error Occured: " + e);
        } 

        return false;
    }

    // save in DB
    protected boolean save(Reg reg){
        String sql = "INSERT INTO users (username, email, password, salt) values (?,?,?,?)";

        try( Connection con = dbConnect();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, reg.getUsername());
            ps.setString(2, reg.getEmail());
            ps.setString(3, reg.getHash());
            ps.setString(4, reg.getSalt());

            return ps.executeUpdate() > 0;
        } catch (Exception e){
            System.out.println(e);
            
        }
        return false;
    }



}