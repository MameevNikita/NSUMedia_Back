package ru.nsu.fit.web.navigation;

import ru.nsu.fit.database.entities.Folder;

public class FolderShortInfoDTO {
    private int id;
    private String name;
    private UserPublicDTO creator;

    public FolderShortInfoDTO(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
        if (folder.getCreator() != null) {
            this.creator = new UserPublicDTO(folder.getCreator());
        }
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
}
