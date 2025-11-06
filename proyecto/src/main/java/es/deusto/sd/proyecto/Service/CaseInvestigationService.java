package es.deusto.sd.proyecto.Service;

import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.DTO.CaseInvestigationDTO;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Date;

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

        int idxLastCase = caseInvestigations.size()-1;
        for(int i = 0; i<N; i++){
            caseInvestigationsList.add(caseInvestigations.get(idxLastCase - i));
        }

        return caseInvestigationsList;
    }

    // return N last caseInvestigations
    public List<CaseInvestigation> getCaseInvestigationsN(UUID token,int N){
        List<CaseInvestigation> caseInvestigations = instance.getCaseInvestigations(token);        

        List<CaseInvestigation> caseInvestigationsList = new ArrayList<>();

        int idxLastCase = caseInvestigations.size()-1;
        for(int i = 0; i<N; i++){
            caseInvestigationsList.add(caseInvestigations.get(idxLastCase - i));
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
