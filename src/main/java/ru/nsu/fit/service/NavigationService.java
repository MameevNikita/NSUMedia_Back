package ru.nsu.fit.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.database.entities.File;
import ru.nsu.fit.database.entities.Folder;
import ru.nsu.fit.database.entities.User;
import ru.nsu.fit.database.repositories.FileRepository;
import ru.nsu.fit.database.repositories.FolderRepository;
import ru.nsu.fit.database.repositories.UserRepository;
import ru.nsu.fit.web.login.UserDTO;
import ru.nsu.fit.web.navigation.FileDTO;
import ru.nsu.fit.web.navigation.FolderDTO;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NavigationService {
    private int rootFolderId;

    private FolderRepository folderRepository;
    private FileRepository fileRepository;
    private UserRepository userRepository;

    public NavigationService(FolderRepository folderRepository, FileRepository fileRepository, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void initializeFolders() {
        Folder rootFolder = folderRepository.findByRoot(true);

        if (rootFolder == null) {
            rootFolder = folderRepository.save(new Folder("root", null, true));
        }

        rootFolderId = rootFolder.getId();
    }

    public int getRootFolderId() {
        return rootFolderId;
    }

    public boolean containsFolder(int folderId) {
        return folderRepository.findById(folderId) != null;
    }

    public Folder getFolderInfo(int folderId) {
        return folderRepository.findById(folderId);
    }

    public boolean containsFile(int fileId) {
        return fileRepository.findById(fileId) != null;
    }

    public File getFileInfo(int fileId) {
        return fileRepository.findById(fileId);
    }

    public Folder createPath(List<String> foldersPath, User creator) {
        Folder folder = folderRepository.findById(getRootFolderId());

        for(String subfolderName : foldersPath){
            Folder subfolder = folderRepository.findByNameAndAndParentFolder(subfolderName, folder);

            if(subfolder == null){
                subfolder = new Folder(subfolderName, creator, false);
                subfolder = folderRepository.save(subfolder);

                subfolder.setParentFolder(folder);
                subfolder = folderRepository.save(subfolder);
            }

            folder = subfolder;
        }

        return folder;
    }

    public File createFile(FileDTO fileInfo, Folder lastFolder, User creator) {
        File file = new File(fileInfo);

        file = fileRepository.save(file);
        file.setParentFolder(lastFolder);
        file.setCreator(creator);

        file = fileRepository.save(file);
        return file;
    }
}
