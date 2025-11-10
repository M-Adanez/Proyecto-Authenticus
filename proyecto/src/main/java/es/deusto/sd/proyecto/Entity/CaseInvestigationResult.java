package es.deusto.sd.proyecto.Entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CaseInvestigationResult extends CaseInvestigation{

    private Map<AnalysisType,List<Float>> results;
    public CaseInvestigationResult(CaseInvestigation ci, Map<AnalysisType,List<Float>> results){
        super(ci);
        this.results = results;
    }

    public Map<AnalysisType,List<Float>> getResults(){
        return this.results;
    }


    @Override
    public String toString() {
        return super.toString() + "{" +
            " results='" + getResults() + "'" +
            "}";
    }

}

