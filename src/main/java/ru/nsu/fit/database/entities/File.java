package ru.nsu.fit.database.entities;

import org.hibernate.validator.constraints.Range;
import ru.nsu.fit.web.navigation.FileDTO;

import javax.persistence.*;

@Entity
@Table(name = "FILES")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parent_folder_id")
    private Folder parentFolder;

    private boolean checked = false;

    private String faculty;
    private String discipline;
    private String documentType;
    private int year;

    @Range(min = 1, max = 5)
    private int readability = 1;

    @Range(min = 1, max = 5)
    private int fullness = 1;

    public File() {
    }

    public File(FileDTO fileDTO) {
        this.name = fileDTO.getName();
        this.faculty = fileDTO.getFaculty();
        this.discipline = fileDTO.getDiscipline();
        this.documentType = fileDTO.getDocumentType();
        this.year = fileDTO.getYear();
        this.readability = fileDTO.getReadability();
        this.fullness = fileDTO.getFullness();
    }

    public File(String name, User creator, Folder parentFolder, String faculty, String discipline, String documentType, int year, int readability, int fullness) {
        this.name = name;
        this.creator = creator;
        this.parentFolder = parentFolder;
        this.faculty = faculty;
        this.discipline = discipline;
        this.documentType = documentType;
        this.year = year;
        this.readability = readability;
        this.fullness = fullness;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getReadability() {
        return readability;
    }

    public void setReadability(int readability) {
        this.readability = readability;
    }

    public int getFullness() {
        return fullness;
    }

    public void setFullness(int fullness) {
        this.fullness = fullness;
    }
}
