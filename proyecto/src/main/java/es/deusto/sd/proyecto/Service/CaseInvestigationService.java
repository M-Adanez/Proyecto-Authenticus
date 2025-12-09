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

    public void createCaseInvestigation(UUID token, CaseInvestigationDTO ciDTO){
        CaseInvestigation ci = DTO_to_CI(ciDTO);
        ci.setUser(instance.getUserByToken(token));
        ciRepository.save(ci);
    }

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

    public CaseInvestigation showCaseInvestigationResults(UUID token, Long ID){
        CaseInvestigation ci = null;
        for(CaseInvestigation c : instance.getCaseInvestigations(token)){
            if(c.getID() == ID) ci = c;
            break;
        }

        int Nimages = ci.getImageList().size();
        Map<AnalysisType,List<Float>> results = new HashMap<>();

        ci.setResults(results);

        List<AnalysisType> types = new ArrayList<>();

        if(ci.getType().equals(AnalysisType.BOTH)){
            types.add(AnalysisType.CONTENT_ALTERATION);
            types.add(AnalysisType.CONTENT_VERACITY);
        }else{
            types.add(ci.getType());
            if(ci.getType().equals(AnalysisType.CONTENT_ALTERATION)){
                results.put(AnalysisType.CONTENT_VERACITY, new ArrayList<Float>(Collections.nCopies(Nimages, -1f)));
            }else{
                results.put(AnalysisType.CONTENT_ALTERATION, new ArrayList<Float>(Collections.nCopies(Nimages, -1f)));
            }
        }

        for(AnalysisType type: types){
            results.put(type, getRandomValues(Nimages));
        }
        
        return new CaseInvestigation(ci);
    }

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
