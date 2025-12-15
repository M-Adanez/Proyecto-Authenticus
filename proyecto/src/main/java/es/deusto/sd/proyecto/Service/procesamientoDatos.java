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
    public CaseInvestigation showCaseInvestigationResults(CaseInvestigation ci) {
    // 1. Inicialización y Preparación
    Map<AnalysisType, List<Float>> results = new HashMap<>();
    ci.setResults(results);

    // 2. Delegación de la lógica de análisis
    switch (ci.getType()) {
        case BOTH:
            return processBoth(ci, results);
        case CONTENT_ALTERATION:
            return processContentAlteration(ci, results);
        case CONTENT_VERACITY:
            return processContentVeracity(ci, results);
        default:
            // Manejar un tipo no válido o nulo si fuera necesario
            return new CaseInvestigation(ci);
    }
    }

    private CaseInvestigation processBoth(CaseInvestigation ci, Map<AnalysisType, List<Float>> results) {
    int Nimages = ci.getImageList().size();
    
    // Simulación del primer tipo de análisis
    results.put(AnalysisType.CONTENT_ALTERATION, getRandomValues(Nimages));
    
    // Simulación del segundo tipo de análisis
    results.put(AnalysisType.CONTENT_VERACITY, getRandomValues(Nimages));
    
    // Retorna una copia del caso con los resultados generados
    return new CaseInvestigation(ci);
    }

    private CaseInvestigation processContentAlteration(CaseInvestigation ci, Map<AnalysisType, List<Float>> results) {
    int Nimages = ci.getImageList().size();
    
    // 1. Simulación del análisis solicitado
    results.put(AnalysisType.CONTENT_ALTERATION, getRandomValues(Nimages));
    
    // 2. Inicialización del tipo opuesto con -1.0f (No aplica)
    results.put(AnalysisType.CONTENT_VERACITY, new ArrayList<Float>(Collections.nCopies(Nimages, -1f)));
    
    // Retorna una copia del caso con los resultados generados
    return new CaseInvestigation(ci);
    }

    private CaseInvestigation processContentVeracity(CaseInvestigation ci, Map<AnalysisType, List<Float>> results) {
    int Nimages = ci.getImageList().size();
    
    // 1. Simulación del análisis solicitado
    results.put(AnalysisType.CONTENT_VERACITY, getRandomValues(Nimages));
    
    // 2. Inicialización del tipo opuesto con -1.0f (No aplica)
    results.put(AnalysisType.CONTENT_ALTERATION, new ArrayList<Float>(Collections.nCopies(Nimages, -1f)));
    
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