package es.deusto.sd.proyecto.DTO;

import java.util.Date;
import java.util.List;

import es.deusto.sd.proyecto.Entity.AnalysisType;

import io.swagger.v3.oas.annotations.media.Schema;

public class CaseInvestigationDTO {
    @Schema(description = "Nombre del Caso de Investigacion", example = "Imagenes editadas")
    private String name;

    @Schema(description = "Tipo de Caso de Investigacion", example = "CONTENT_ALTERATION")
    private AnalysisType type;
    
    @Schema(description = "Fecha del Caso de Investigacion", example = "23/11/2025")
    private Date date;

    @Schema(description = "Lista de URLs de las Imagaenes", example = "/File/Images/image.jpg")
    private List<String> imageList; 
    
    public CaseInvestigationDTO(){}

    public CaseInvestigationDTO(String name, AnalysisType type, Date date, List<String> imageList) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.imageList = imageList;
    }

    public CaseInvestigationDTO(String name, AnalysisType type, Date date) {
        this.name = name;
        this.type = type;
        this.date = date;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnalysisType getType() {
        return this.type;
    }

    public void setType(AnalysisType type) {
        this.type = type;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getImageList() {
        return this.imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
   

}
