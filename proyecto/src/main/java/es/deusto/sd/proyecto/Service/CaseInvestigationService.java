package es.deusto.sd.proyecto.Service;

import es.deusto.sd.proyecto.Entity.CaseInvestigation;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Date;

@Service
public class CaseInvestigationService{

    static int id = 0;

    private List<CaseInvestigation> caseInvestigations = new ArrayList<>();

    public CaseInvestigationService() {
    }

    public void createCaseInvestigation(CaseInvestigation ci){
        caseInvestigations.add(ci);
    }

    // return 5 last caseInvestigations
    public List<CaseInvestigation> getCaseInvestigations(){
        List<CaseInvestigation> caseInvestigationsList = new ArrayList<>();
        int N = 5;

        int idxLastCase = caseInvestigations.size()-1;
        for(int i = 0; i<N; i++){
            caseInvestigationsList.add(caseInvestigations.get(idxLastCase - i));
        }

        return caseInvestigationsList;
    }

    // return N last caseInvestigations
    public List<CaseInvestigation> getCaseInvestigationsN(int N){
        List<CaseInvestigation> caseInvestigationsList = new ArrayList<>();

        int idxLastCase = caseInvestigations.size()-1;
        for(int i = 0; i<N; i++){
            caseInvestigationsList.add(caseInvestigations.get(idxLastCase - i));
        }

        return caseInvestigationsList;
    }
    
    // return caseInvestigations between startDate and endDate
    public List<CaseInvestigation> getCaseInvestigationsInDate(Date startDate, Date endDate){
        List<CaseInvestigation> caseInvestigationsList = new ArrayList<>();

        for(CaseInvestigation caseInv : caseInvestigations){
            if(caseInv.getDate().before(endDate) && caseInv.getDate().after(startDate)) caseInvestigationsList.add(caseInv);
        }

        return caseInvestigationsList;
    }

    public void deleteCaseInvestigation(int ID){
        for(int i = 0; i<caseInvestigations.size(); i++){
            if(caseInvestigations.get(i).getID() == ID){
                caseInvestigations.remove(i);
                break;
            }
        }
    }

    public void addFilesToCase(List<String> filesURL, int ID){
        caseInvestigations.get(ID).setImageList(filesURL);
    }
}
