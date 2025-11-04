package es.deusto.sd.proyecto.Service;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.deusto.sd.proyecto.DTO.userDTO;
import es.deusto.sd.proyecto.Entity.User;

import java.util.UUID;

@Service
public class userService {

    private stateManagement instance;
    public userService() {
        instance = stateManagement.getInstance();
    }

    public UUID generateToken(){
        UUID uuid=UUID.randomUUID();
        return uuid;
    }

    public String registerUser(userDTO userDTO){
        User user= new User(userDTO);
        if (user.getUsername() == null || user.getUsername().isEmpty() ||
            user.getPassword() == null || user.getPassword().isEmpty() ||
            user.getNombre() == null || user.getNombre().isEmpty() ||
            user.getTlf() == null || user.getTlf().isEmpty()) {
            return "faltan datos"; // Bad Request
        }
        
        if (instance.getUsers().contains(user)){
            return "ya registrado";
        }
        instance.addUser(user);
        return "creado";
    }
    
    public String loginUser(userDTO userDTO){
        if (instance.getLoggedUssers().values()!=null){
            return "ya logeado";//YA LOGEADO
        }

        User user=new User(userDTO);
        if (stateManagement.users.contains(user)){
            stateManagement.current_user=user;
            return "bien";
        }

        ArrayList<String> usernames=new ArrayList<>();
        for (User us:stateManagement.users){
            usernames.add(us.getUsername());
        }
        if(usernames.contains(user.getUsername())){
            return "contraseña mal";
        }

        return "faltan datos";
    }

    public String logout(){
        if (stateManagement.current_user==null){
            return "mal"; //NO LOGEADO
        }else{
            stateManagement.current_user=null;
            return "bien";

        }
    }
}
