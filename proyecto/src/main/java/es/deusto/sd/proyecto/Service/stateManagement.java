package es.deusto.sd.proyecto.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.Entity.User;

public class stateManagement {
	private static volatile stateManagement instance;

    private List<User> users;
    private Map<UUID,User> user_token;
    private Map<User, List<CaseInvestigation>> users_case_investigations;

    private stateManagement() {
		this.users = new ArrayList<>();
		this.user_token = new HashMap<>();
        users_case_investigations = new HashMap<>();
	}

    public static stateManagement getInstance() {
		if (instance == null) {
			synchronized (stateManagement.class) {
				if (instance == null) {
					instance = new stateManagement();
				}
			}
		}
		return instance;
	}

    public synchronized void addUser(User user){
        users.add(user);
        users_case_investigations.put(user, new ArrayList<CaseInvestigation>());
    }

    public Map<UUID, User> getLoggedUssers(){
        return this.user_token;
    }

    public List<User> getUsers(){
        return this.users;
    }


    public synchronized void addLogin(UUID token, User user){
        user_token.put(token, user);
    }

    public User getUser(UUID token){
        return user_token.get(token);
    }

    public synchronized void addCaseInvestigation(UUID token, CaseInvestigation ci){
        User us = getUserByToken(token);
        users_case_investigations.get(us).add(ci);
    }

    public List<CaseInvestigation> getCaseInvestigations(UUID token){
        User us = getUserByToken(token);
        return users_case_investigations.get(us);
    }


    public User getUserByToken(UUID token){
        User us = user_token.get(token);
        return us;
    }

    public synchronized void delete_token(UUID token){
        user_token.remove(token);
    }

}
