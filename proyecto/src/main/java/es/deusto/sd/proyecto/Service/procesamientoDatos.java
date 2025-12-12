package es.deusto.sd.proyecto.Service;

import es.deusto.sd.proyecto.Entity.CaseInvestigation;
import es.deusto.sd.proyecto.Entity.AnalysisType;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class procesamientoDatos {

    // El método ahora recibe el CaseInvestigation ya validado y listo para procesar.
    // La lógica de token, ID y validación del usuario debe quedarse en CaseInvestigationService.
    
    /**
     * Simula el procesamiento de un caso de investigación, generando resultados aleatorios
     * basados en el tipo de análisis solicitado.
     * @param ci El CaseInvestigation a procesar.
     * @return Una nueva instancia de CaseInvestigation con los resultados de la simulación.
     */
    public CaseInvestigation showCaseInvestigationResults(CaseInvestigation ci){
        
        // --- 1. Lógica de Análisis y Preparación de Resultados ---
        
        int Nimages = ci.getImageList().size();
        Map<AnalysisType,List<Float>> results = new HashMap<>();

        ci.setResults(results);

        List<AnalysisType> types = new ArrayList<>();

        if(ci.getType().equals(AnalysisType.BOTH)){
            types.add(AnalysisType.CONTENT_ALTERATION);
            types.add(AnalysisType.CONTENT_VERACITY);
        } else {
            types.add(ci.getType());
            // Inicializa el tipo opuesto con -1f (No aplica)
            if(ci.getType().equals(AnalysisType.CONTENT_ALTERATION)){
                results.put(AnalysisType.CONTENT_VERACITY, new ArrayList<Float>(Collections.nCopies(Nimages, -1f)));
            } else {
                results.put(AnalysisType.CONTENT_ALTERATION, new ArrayList<Float>(Collections.nCopies(Nimages, -1f)));
            }
        }
        
        // --- 2. Simulación de Resultados ---

        for(AnalysisType type: types){
            results.put(type, getRandomValues(Nimages));
        }
        
        // Retorna una copia del caso con los resultados generados
        return new CaseInvestigation(ci);
    }
    
    /**
     * Genera N valores flotantes aleatorios entre 0.0 y 1.0 (simulación de resultados).
     */
    private List<Float> getRandomValues(int N){
        List<Float> values = new ArrayList<>();
        Random ran = new Random();
        for(int i = 0; i<N; i++){
            values.add(ran.nextFloat());
        }
        return values;
    }
}