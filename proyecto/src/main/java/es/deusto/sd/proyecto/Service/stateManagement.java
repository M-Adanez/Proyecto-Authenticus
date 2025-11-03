package es.deusto.sd.proyecto.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.deusto.sd.proyecto.Entity.User;

public class stateManagement {
    public static User current_user;
    private static List<User> users=new ArrayList<>();
    private static Map<UUID,User> user_token= new HashMap<>();
}
