package es.deusto.sd.proyecto.Service;

import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.DAO.caseInvestigationRepository;
import es.deusto.sd.proyecto.DTO.CaseInvestigationDTO;

import es.deusto.sd.proyecto.Entity.AnalysisType;

import org.aspectj.internal.lang.annotation.ajcDeclareSoft;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CaseInvestigationService{

    private final caseInvestigationRepository ciRepository;
    private final stateManagement instance;

    public CaseInvestigationService(caseInvestigationRepository ciRepository) {
        this.ciRepository = ciRepository;
        this.instance = stateManagement.getInstance();
    }

    //el createCaseInvestigation se hace en gestionBBDD

    // return 5 last caseInvestigations
    public List<CaseInvestigation> getCaseInvestigations(UUID token){
        List<CaseInvestigation> caseInvestigations = ciRepository.findAllByUser(instance.getUserByToken(token));

        List<CaseInvestigation> caseInvestigationsList = new ArrayList<>();
        int N = 5;
        
        for(int i = 0; i<N && i<caseInvestigations.size(); i++){
            caseInvestigationsList.add(caseInvestigations.reversed().get(i));
        }

        return caseInvestigationsList;
    }

    // return N last caseInvestigations
    public List<CaseInvestigation> getCaseInvestigationsN(UUID token,int N){
        List<CaseInvestigation> caseInvestigations = ciRepository.findAllByUser(instance.getUserByToken(token));       

        List<CaseInvestigation> caseInvestigationsList = new ArrayList<>();

        for(int i = 0; i<N && i<caseInvestigationsList.size(); i++){
            caseInvestigationsList.add(caseInvestigations.reversed().get(i));
        }

        return caseInvestigationsList;
    }
    
    // return caseInvestigations between startDate and endDate
    public List<CaseInvestigation> getCaseInvestigationsInDate(UUID token, Date startDate, Date endDate){
        List<CaseInvestigation> caseInvestigations = ciRepository.findAllByUser(instance.getUserByToken(token));

        List<CaseInvestigation> caseInvestigationsList = new ArrayList<>();

        for(CaseInvestigation caseInv : caseInvestigations){
            if(caseInv.getDate().before(endDate) && caseInv.getDate().after(startDate)) caseInvestigationsList.add(caseInv);
        }

        return caseInvestigationsList;
    }

    // se elimina solo si el ci corresponde al usuario con el UUID
    public void deleteCaseInvestigation(UUID token, Long ID){
        Optional<CaseInvestigation> ci = ciRepository.findById(ID);

        if(ci.isPresent()){
            if(ci.get().getUser().equals(instance.getUserByToken(token))){
                ciRepository.deleteById(ID);
            }
        }

    }

    public void addFilesToCase(UUID token, List<String> filesURL, Long ID){
        Optional<CaseInvestigation> ci = ciRepository.findById(ID);

        if(ci.isPresent()){
            ci.get().setImageList(filesURL);

            ciRepository.save(ci.get());
        }

    }

    // MOVER AL SERVICIO PROCESAMIENTO DE DATOS Y SOLUCIONAR

    //movido el showCaseInvestigationResults a procesamientoDatos

    private List<Float> getRandomValues(int N){
        List<Float> values = new ArrayList<>();
        Random ran = new Random();
        for(int i = 0; i<N; i++){
            values.add(ran.nextFloat());
        }
        return values;
    }

    // DTO parsers

    public CaseInvestigation DTO_to_CI(CaseInvestigationDTO dto){
        CaseInvestigation ci = new CaseInvestigation(dto.getName(), dto.getType(), dto.getDate(), dto.getImageList());
        return ci;
    }

    public CaseInvestigationDTO CI_to_DTO(CaseInvestigation ci){
        CaseInvestigationDTO dto = new CaseInvestigationDTO(ci.getName(), ci.getType(), ci.getDate(), ci.getImageList());
        return dto;
    } 
}
