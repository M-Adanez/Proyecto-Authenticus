package es.deusto.sd.proyecto.Entity;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
// nombre del caso, tipo de análisis (alteración de contenido, veracidad de un contenido o ambos),
// fecha y lista de archivos de imagen (incluyendo la ruta al archivo).

public class CaseInvestigation{

    private enum AnalysisType{
        CONTENT_ALETARION, CONTENT_VERACITY, BOTH;
    }

    private static int ID = 0;

    private String name;
    private AnalysisType type;
    private Date date;
    private List<String> imageList;

    // void constructor
    public CaseInvestigation(){
        ID++;
    }

    // constructor with all args
    public CaseInvestigation(String name, AnalysisType type, Date date, List<String> imageList) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.imageList = imageList;
        ID++;
    }

    // constructor without imageList 
    public CaseInvestigation(String name, Date date, AnalysisType type) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.imageList = new ArrayList<>(); 
        ID++;
    }

    // getters and setters
    public static int getID() {
        return ID;
    }

    public static void setID(int id) {
        ID = id;
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