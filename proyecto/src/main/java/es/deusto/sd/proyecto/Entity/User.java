package es.deusto.sd.proyecto.Entity;

import es.deusto.sd.proyecto.DTO.userDTO;

public class User {
    private String username;
    private String password;


    public User(String user,String pass){
    this.username=user;
    this.password=pass;
    }
    public User(userDTO userDTO){
        this.username=userDTO.getUsername();
        this.password=userDTO.getPassword();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        User other=(User) obj;
        return username.equals(other.username) && password.equals(other.password);
    }



    
}
