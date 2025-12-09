package es.deusto.sd.proyecto.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.deusto.sd.proyecto.Entity.User;

@Repository
public interface userRepository extends JpaRepository<User, Integer>{
    
}
