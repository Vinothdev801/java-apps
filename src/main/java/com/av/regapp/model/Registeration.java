package main.java.com.av.regapp.model;

public class Registeration {
    private String username;
    private String email;
    private String salt;
    private String hashPassword;

    public Registeration(String username, String email, String salt, String hashPassword){
        this.username = username;
        this.email = email;
        this.salt = salt;
        this.hashPassword = hashPassword;
    }

    public String getUsername() { 
        return username;
    }
    public void setUsername(String username) { 
        this.username = username; 
    }
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
    public String getSalt() { 
        return salt; 
    }
    public void setSalt(String salt) { 
        this.salt = salt; 
    }
    public String getHash() { 
        return hashPassword; 
    }
    public void setHash(String hashPassword) { 
        this.hashPassword = hashPassword; 
    }
}
