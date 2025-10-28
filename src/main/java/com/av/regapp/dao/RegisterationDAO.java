package main.java.com.av.regapp.dao;


import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.av.regapp.model.Registeration;
import main.java.com.av.regapp.utils.SHAHasing;

public class RegisterationDAO{
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection dbConnection;

    private static final Logger log = LogManager.getLogger(RegisterationDAO.class);


    public RegisterationDAO(String jdbcUrl, String jdbcUsername, String jdbcPassword){
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

                log.info("DB connection successful!");
            } catch (Exception e) {
                log.error("DB connection failed: {}", e);
                throw new SQLException(e);
            }

        }
        return dbConnection;
    }

    // Disconnect Database
    protected void dbDisconnect() throws SQLException{
        if(dbConnection != null && !dbConnection.isClosed()){
            dbConnection.close();
            log.info("DB connection closed.");
        }
    }

    // get data from DB
    public boolean getData(String uname, String pass){
        String sql = "SELECT * FROM users where username = ?";

        try(Connection con = dbConnect();
            PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setString(1, uname);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                String hash = SHAHasing.hash(pass, rs.getString("salt"));

                log.info("Data fetched successfully.");
                return rs.getString("password").equals(hash);
            }
            
        } catch (Exception e) {
            log.error("Data Fetch Error: {}",e);
        } 

        return false;
    }

    // save in DB
    public boolean save(Registeration reg){
        String sql = "INSERT INTO users (username, email, password, salt) values (?,?,?,?)";

        try( Connection con = dbConnect();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, reg.getUsername());
            ps.setString(2, reg.getEmail());
            ps.setString(3, reg.getHash());
            ps.setString(4, reg.getSalt());

            log.info("user data added to DB: {}", reg.getUsername());

            return ps.executeUpdate() > 0;
        } catch (Exception e){
            log.error("failed to store user data in DB: {}", e);
            
        }
        return false;
    }



}