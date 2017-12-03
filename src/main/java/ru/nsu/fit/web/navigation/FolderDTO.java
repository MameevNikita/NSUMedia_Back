package ru.nsu.fit.web.navigation;

import ru.nsu.fit.database.entities.Folder;

import java.util.List;
import java.util.stream.Collectors;

public class FolderDTO {
    private int id;
    private String name;
    private UserPublicDTO creator;
    private FolderShortInfoDTO parentFolder;
    private List<FolderShortInfoDTO> subfolders;
    private List<FileDTO> files;

    public FolderDTO(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
        if (folder.getCreator() != null) {
            this.creator = new UserPublicDTO(folder.getCreator());
        }
        if (folder.getParentFolder() != null) {
            this.parentFolder = new FolderShortInfoDTO(folder.getParentFolder());
        }
        this.subfolders = folder.getSubfolders().stream().map(FolderShortInfoDTO::new).collect(Collectors.toList());
        this.files = folder.getFiles().stream().map(FileDTO::new).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public FolderShortInfoDTO getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(FolderShortInfoDTO parentFolder) {
        this.parentFolder = parentFolder;
    }

    public List<FolderShortInfoDTO> getSubfolders() {
        return subfolders;
    }

    public void setSubfolders(List<FolderShortInfoDTO> subfolders) {
        this.subfolders = subfolders;
    }

    public List<FileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FileDTO> files) {
        this.files = files;
    }
}
