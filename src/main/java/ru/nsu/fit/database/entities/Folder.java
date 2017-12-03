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

    public Integer getId() {
        return id;
    }

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



    private boolean root = false;

    public Set<Folder> getSubfolders() {
        return subfolders;
    }

    public Set<File> getFiles() {
        return files;
    }

    public Folder(String name, User creator, boolean root) {
        this.name = name;
        this.creator = creator;
        this.root = root;
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
