package com.example.vhsince81;

public class User {


   public String username;
   public String email;
   public String password;

    User( String username,String email,String password)
    {

        this.username = username;
        this.email = email;
        this.password  = password;
    }

    String getUsername()
    {
        return username;
    }
    String getEmail()
    {
        return email;
    }
    String getPassword(){return password; }

}
