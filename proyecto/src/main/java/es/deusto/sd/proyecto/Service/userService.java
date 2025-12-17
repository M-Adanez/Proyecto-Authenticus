package es.deusto.sd.proyecto.Service;

import java.util.ArrayList;

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

    public UUID generateToken(User user){
        UUID uuid=UUID.randomUUID();
        instance.addLogin(uuid,user);
        return uuid;
    }
    

    public APIResponse registerUser(userDTO userDTO){
        User user= new User(userDTO);
        if (user.getUsername() == null || user.getUsername().isEmpty() ||
            user.getPassword() == null || user.getPassword().isEmpty() ||
            user.getNombre() == null || user.getNombre().isEmpty() ||
            user.getTlf() == null || user.getTlf().isEmpty()) {
            return APIResponse.FALTAN_DATOS; // Bad Request
        }
        
        if (instance.getUsers().contains(user)){
            return APIResponse.YA_REGISTRADO;
        }
        instance.addUser(user);
        return APIResponse.CREADO;
    }


    
    public APIResponse loginUser(userDTO userDTO){
        if (instance.getLoggedUssers().values().contains(new User(userDTO))){
            return APIResponse.YA_LOGEADO;
        }

        User user=new User(userDTO);

        if (instance.getUsers().contains(user)){
            return APIResponse.BIEN;
        }

        ArrayList<String> usernames=new ArrayList<>();
        for (User us:instance.getUsers()){
            usernames.add(us.getUsername());
        }
        if(usernames.contains(user.getUsername())){
            return APIResponse.CONTRASEÑA_MAL;
        }

        return APIResponse.FALTAN_DATOS;
    }

    public APIResponse logout(String token){
        if (this.instance.getLoggedUssers().containsKey(UUID.fromString(token))){
            instance.delete_token(UUID.fromString(token));
            return APIResponse.BIEN;
        }else{
            return APIResponse.MAL;
        }
    }

    public APIResponse remove(String token){
        if(this.instance.getLoggedUssers().containsKey(UUID.fromString(token))){
            instance.delete_user(UUID.fromString(token));
            logout(token);
            return APIResponse.BIEN;
        }else{
            return APIResponse.NO_EXISTE;
        }
    }
}
