package ru.nsu.fit.database.entities;

import org.hibernate.validator.constraints.Range;

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
    private int readability;

    @Range(min = 1, max = 5)
    private int fullness;

    public File() {
    }

    public File(String name, User creator, Folder parentFolder) {
        this.name = name;
        this.creator = creator;
        this.parentFolder = parentFolder;
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


}
