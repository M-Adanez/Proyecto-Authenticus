package es.deusto.sd.proyecto.Service;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import es.deusto.sd.proyecto.DTO.userDTO;
import es.deusto.sd.proyecto.Entity.User;

import java.util.UUID;

@Service
public class userService {
    private final ArrayList<User> users=new ArrayList<>();
    private final Map<UUID,User> user_token= new HashMap<>();

    public userService() {
    }

    public UUID generateToken(){
        UUID uuid=UUID.randomUUID();
        return uuid;
    }

    public int registerUser(userDTO userDTO){
        User user= new User(userDTO);
        if(user.getUsername()=="" || user.getPassword()=="" || user.getUsername()==null || user.getPassword()==null ){
            return 400;
        }
        if (users.contains(user)){
            return 409;
        }
        users.add(user);
        return 200;//FALTA COMPORBAR SI YA ESTA
    }
    
    public int loginUser(userDTO userDTO){
        User user=new User(userDTO.getUsername(), userDTO.getPassword());
        if (users.contains(user)){
            return 200;
        }
        ArrayList<String> usernames=new ArrayList<>();
        for (User us:users){
            usernames.add(us.getUsername());
        }
        if(usernames.contains(user.getUsername())){
            return 401;
        }

        return 404;
    }
}
