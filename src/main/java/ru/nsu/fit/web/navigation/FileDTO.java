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
        this.id = file.getId();
        this.name = file.getName();
        if (file.getCreator() != null) {
            this.creator = new UserPublicDTO(file.getCreator());
        }
        this.checked = file.isChecked();
        this.faculty = file.getFaculty();
        this.discipline = file.getDiscipline();
        this.documentType = file.getDocumentType();
        this.year = file.getYear();
        this.readability = file.getReadability();
        this.fullness = file.getFullness();
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
