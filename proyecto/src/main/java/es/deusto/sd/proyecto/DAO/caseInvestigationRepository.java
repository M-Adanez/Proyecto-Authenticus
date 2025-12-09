package es.deusto.sd.proyecto.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.Entity.User;
import java.util.List;

@Repository
public interface caseInvestigationRepository extends JpaRepository<CaseInvestigation, Long>{
    List<CaseInvestigation> findAllByUser(User user); // FALTA IMPLEMENTAR

}
