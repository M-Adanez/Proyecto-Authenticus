package es.deusto.sd.proyecto.Service;

import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.Entity.CaseInvestigationResult;
import es.deusto.sd.proyecto.DTO.CaseInvestigationDTO;

import es.deusto.sd.proyecto.Entity.AnalysisType;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CaseInvestigationService{
    private stateManagement instance;

    public CaseInvestigationService() {
        this.instance = stateManagement.getInstance();
    }

    public void createCaseInvestigation(UUID token, CaseInvestigationDTO ciDTO){
        instance.addCaseInvestigation(token, DTO_to_CI(ciDTO));
    }

    // return 5 last caseInvestigations
    public List<CaseInvestigation> getCaseInvestigations(UUID token){
        List<CaseInvestigation> caseInvestigations = instance.getCaseInvestigations(token);

        List<CaseInvestigation> caseInvestigationsList = new ArrayList<>();
        int N = 5;
        
        for(int i = 0; i<N && i<caseInvestigations.size(); i++){
            caseInvestigationsList.add(caseInvestigations.reversed().get(i));
        }

        return caseInvestigationsList;
    }

    // return N last caseInvestigations
    public List<CaseInvestigation> getCaseInvestigationsN(UUID token,int N){
        List<CaseInvestigation> caseInvestigations = instance.getCaseInvestigations(token);        

        List<CaseInvestigation> caseInvestigationsList = new ArrayList<>();

        for(int i = 0; i<N && i<caseInvestigationsList.size(); i++){
            caseInvestigationsList.add(caseInvestigations.reversed().get(i));
        }

        return caseInvestigationsList;
    }
    
    // return caseInvestigations between startDate and endDate
    public List<CaseInvestigation> getCaseInvestigationsInDate(UUID token, Date startDate, Date endDate){
        List<CaseInvestigation> caseInvestigations = instance.getCaseInvestigations(token);

        List<CaseInvestigation> caseInvestigationsList = new ArrayList<>();

        for(CaseInvestigation caseInv : caseInvestigations){
            if(caseInv.getDate().before(endDate) && caseInv.getDate().after(startDate)) caseInvestigationsList.add(caseInv);
        }

        return caseInvestigationsList;
    }

    public void deleteCaseInvestigation(UUID token, int ID){
        List<CaseInvestigation> caseInvestigations = instance.getCaseInvestigations(token);        

        for(int i = 0; i<caseInvestigations.size(); i++){
            if(caseInvestigations.get(i).getID() == ID){
                caseInvestigations.remove(i);
                break;
            }
        }
    }

    public void addFilesToCase(UUID token, List<String> filesURL, int ID){
        List<CaseInvestigation> caseInvestigations = instance.getCaseInvestigations(token);        

        caseInvestigations.get(ID).setImageList(filesURL);
    }

    public CaseInvestigationResult showCaseInvestigationResults(UUID token, int ID){
        CaseInvestigation ci = null;
        for(CaseInvestigation c : instance.getCaseInvestigations(token)){
            if(c.getID() == ID) ci = c;
            break;
        }

        int Nimages = ci.getImageList().size();
        Map<AnalysisType,List<Float>> results = new HashMap<>();

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
        
        return new CaseInvestigationResult(ci, results);
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
