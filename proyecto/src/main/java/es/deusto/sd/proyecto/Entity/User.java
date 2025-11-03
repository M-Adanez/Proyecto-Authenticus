package es.deusto.sd.proyecto.Entity;

import es.deusto.sd.proyecto.DTO.userDTO;

public class User {
    private String username;//EMAIL
    private String password;
    private String nombre;
    private String tlf;


    public User(String user,String pass){
    this.username=user;
    this.password=pass;
    }

    public User(userDTO userDTO){
        this.username=userDTO.getUsername();
        this.password=userDTO.getPassword();
        this.nombre=userDTO.getNombre();
        this.tlf=userDTO.getTlf();
    }

    public User(String username, String password, String nombre, String tlf) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.tlf = tlf;
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

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTlf() {
        return this.tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return java.util.Objects.equals(username, other.username) &&
               java.util.Objects.equals(password, other.password) &&
               java.util.Objects.equals(nombre, other.nombre) &&
               java.util.Objects.equals(tlf, other.tlf);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(username, password, nombre, tlf);
    }

}
