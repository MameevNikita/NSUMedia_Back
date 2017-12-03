package ru.nsu.fit.web.navigation;

import ru.nsu.fit.database.entities.File;

public class FileDTO {
    private Integer id;
    private String name;
    private UserPublicDTO creator;
    private boolean checked = false;
    private String faculty;
    private String discipline;
    private String documentType;
    private int year;
    private int readability;
    private int fullness;

    public FileDTO(File file) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.checked = checked;
        this.faculty = faculty;
        this.discipline = discipline;
        this.documentType = documentType;
        this.year = year;
        this.readability = readability;
        this.fullness = fullness;
    }

    public FileDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserPublicDTO getCreator() {
        return creator;
    }

    public void setCreator(UserPublicDTO creator) {
        this.creator = creator;
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
