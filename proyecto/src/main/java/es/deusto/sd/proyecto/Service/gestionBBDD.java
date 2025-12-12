package es.deusto.sd.proyecto.Service;

import org.springframework.stereotype.Service;

import es.deusto.sd.proyecto.DAO.userRepository;
import es.deusto.sd.proyecto.DAO.caseInvestigationRepository; // Necesario para createCaseInvestigation
import es.deusto.sd.proyecto.DTO.userDTO;
import es.deusto.sd.proyecto.Entity.User;
import es.deusto.sd.proyecto.Entity.CaseInvestigation; // Necesario para createCaseInvestigation

import java.util.UUID;

@Service
public class gestionBBDD {

    private final userRepository usRepository;
    private final caseInvestigationRepository ciRepository; // Repositorio de casos
    private final stateManagement instance;

    /**
     * Constructor para inyección de dependencias. 
     * Recibe los repositorios necesarios para las operaciones de persistencia.
     */
    public gestionBBDD(userRepository usRepository, caseInvestigationRepository ciRepository) {
        this.usRepository = usRepository;
        this.ciRepository = ciRepository;
        this.instance = stateManagement.getInstance();
    }

    // --- 1. Método: registerUser (Gestión de Usuario) ---

    public String registerUser(userDTO userDTO){
        User user= new User(userDTO);
        
        // 1. Validación de datos faltantes
        if (user.getUsername() == null || user.getUsername().isEmpty() ||
            user.getPassword() == null || user.getPassword().isEmpty() ||
            user.getNombre() == null || user.getNombre().isEmpty() ||
            user.getTlf() == null || user.getTlf().isEmpty()) {
            return "faltan datos";
        }
        
        // 2. Comprobación si ya está registrado
        if (usRepository.findAll().contains(user)){
            return "ya registrado";
        }
        
        // 3. Guardar
        usRepository.save(user);
        return "creado";
    }

    // --- 2. Método: deleteUser (Gestión de Usuario) ---

    public void deleteUser(UUID token){
        // Utiliza stateManagement para obtener el usuario asociado al token
        User userToDelete = this.instance.getUserByToken(token);
        if (userToDelete != null) {
            usRepository.delete(userToDelete);
            // Opcional: Desloguear al usuario después de la eliminación si se desea
            this.instance.logout(token);
        }
    }

    // --- 3. Método: createCaseInvestigation (Gestión de Casos) ---

    /**
     * Guarda una nueva investigación de caso en la base de datos.
     * La lógica de asignación de usuario debe venir del servicio llamador (CaseInvestigationService).
     * @param ci El objeto CaseInvestigation listo para guardar.
     */
    public void createCaseInvestigation(CaseInvestigation ci){
        ciRepository.save(ci);
    }
}