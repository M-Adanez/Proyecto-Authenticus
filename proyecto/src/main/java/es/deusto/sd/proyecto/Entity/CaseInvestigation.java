package es.deusto.sd.proyecto.Entity;

import es.deusto.sd.proyecto.Entity.AnalysisType;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
// nombre del caso, tipo de análisis (alteración de contenido, veracidad de un contenido o ambos),
// fecha y lista de archivos de imagen (incluyendo la ruta al archivo).

public class CaseInvestigation{

    private static int ID_cont = 0;

    private int ID;
    private String name;
    private AnalysisType type;
    private Date date;
    private List<String> imageList;

    // void constructor
    public CaseInvestigation(){
        this.ID = ID_cont++;
    }

    // constructor with all args
    public CaseInvestigation(String name, AnalysisType type, Date date, List<String> imageList) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.imageList = imageList;
        this.ID = ID_cont++;
    }

    // constructor without imageList 
    public CaseInvestigation(String name, Date date, AnalysisType type) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.imageList = new ArrayList<>(); 
        this.ID = ID_cont++;
    }

    // getters and setters
    public int getID() {
        return this.ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnalysisType getType() {
        return type;
    }

    public void setType(AnalysisType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
    
}