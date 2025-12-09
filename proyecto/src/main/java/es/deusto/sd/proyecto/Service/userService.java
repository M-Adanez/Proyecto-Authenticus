package es.deusto.sd.proyecto.Service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import es.deusto.sd.proyecto.DAO.userRepository;
import es.deusto.sd.proyecto.DTO.userDTO;
import es.deusto.sd.proyecto.Entity.User;

import java.util.UUID;

@Service
public class userService {

    private final userRepository usRepository;
    private final stateManagement instance;

    public userService(userRepository usRepository) {
        this.usRepository = usRepository;
        this.instance = stateManagement.getInstance();
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
        
        if (usRepository.findAll().contains(user)){
            return "ya registrado";
        }
        usRepository.save(user);
        return "creado";
    }

    //Podriamos comprobar que exista el us
    public String loginUser(userDTO userDTO){
        if (instance.getLoggedUssers().values().contains(new User(userDTO))){ 
            return "ya logeado"; //YA LOGEADO
        }

        User user=new User(userDTO);
        UUID tok=generateToken();
        instance.addLogin(tok,user);

        if (usRepository.findAll().contains(user)){
            return tok.toString();
        }

        ArrayList<String> usernames=new ArrayList<>();
        for (User us:usRepository.findAll()){
            usernames.add(us.getUsername());
        }
        if(usernames.contains(user.getUsername())){
            return "contraseña mal"; //existe username pero no coincide con password
        }

        return "faltan datos";
    }

    public void deleteUser(UUID token){
        usRepository.delete(instance.getUserByToken(token));
    }

    public String logout(String token){
        if (this.instance.getLoggedUssers().containsKey(UUID.fromString(token))){
            instance.logout(UUID.fromString(token));
            return "bien";
        }else{
            return "mal";
        }
    }
}
