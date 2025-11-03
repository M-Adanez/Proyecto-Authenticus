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
    public User current_user;
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
        if(user.getUsername()==null || user.getPassword()==null){
            if(user.getUsername()=="" || user.getPassword()==""){
                return 400;
            }
        }

        if (users.contains(user)){
            return 409;
        }
        users.add(user);
        return 201;
    }
    
    public int loginUser(userDTO userDTO){
        User user=new User(userDTO.getUsername(), userDTO.getPassword());
        if (users.contains(user)){
            current_user=user;
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

    public int logout(){
        if (current_user==null){
            return 400;
        }else{
            return 200;
        }
    }
}
