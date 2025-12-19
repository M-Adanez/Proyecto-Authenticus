package es.deusto.sd.proyecto.Entity;

import es.deusto.sd.proyecto.Entity.AnalysisType;

import java.util.Date;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import jakarta.persistence.*;

// nombre del caso, tipo de análisis (alteración de contenido, veracidad de un contenido o ambos),
// fecha y lista de archivos de imagen (incluyendo la ruta al archivo).
@Entity
@Table(name = "case_investigation")
public class CaseInvestigation implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING) 
    private AnalysisType type;

    private Date date;

    @ElementCollection 
    private List<String> imageList;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // void constructor
    public CaseInvestigation(){
    }

    // copy constructor
    public CaseInvestigation(CaseInvestigation copy){
        this.id = copy.getID();
        this.name = copy.getName();
        this.date = copy.getDate();
        this.type = copy.getType();
        this.imageList = copy.getImageList();
    }

    // constructor with all args
    public CaseInvestigation(String name, AnalysisType type, Date date, List<String> imageList) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.imageList = imageList;
    }

    // constructor without imageList 
    public CaseInvestigation(String name, Date date, AnalysisType type) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.imageList = new ArrayList<>(); 
    }

    // getters and setters
    public long getID() {
        return this.id;
    }

    public void setID(long id) {
        this.id = id;
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

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    @Override
    public String toString() {
        return "{" +
            " ID='" + getID() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", date='" + getDate() + "'" +
            ", imageList='" + getImageList() + "'" +
            "}";
    }


}