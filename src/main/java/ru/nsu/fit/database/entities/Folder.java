package ru.nsu.fit.database.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FOLDERS")
public class Folder {
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

    @OneToMany(mappedBy = "parentFolder")
    private Set<Folder> subfolders = new HashSet<>();

    @OneToMany(mappedBy = "parentFolder")
    private Set<File> files = new HashSet<>();


    public Folder(String name, User creator, Folder parentFolder) {
        this.name = name;
        this.creator = creator;
        this.parentFolder = parentFolder;
    }

    public Folder(String name, User creator) {
        this.name = name;
        this.creator = creator;
    }

    public Folder() {
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
