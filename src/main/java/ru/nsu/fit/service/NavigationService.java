package ru.nsu.fit.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.database.entities.File;
import ru.nsu.fit.database.entities.FileData;
import ru.nsu.fit.database.entities.Folder;
import ru.nsu.fit.database.entities.User;
import ru.nsu.fit.database.repositories.FileDataRepository;
import ru.nsu.fit.database.repositories.FileRepository;
import ru.nsu.fit.database.repositories.FolderRepository;
import ru.nsu.fit.database.repositories.UserRepository;
import ru.nsu.fit.web.login.UserDTO;
import ru.nsu.fit.web.navigation.FileDTO;
import ru.nsu.fit.web.navigation.FolderDTO;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class NavigationService {
    private int rootFolderId;

    private FolderRepository folderRepository;
    private FileRepository fileRepository;
    private UserRepository userRepository;
    private FileDataRepository fileDataRepository;

    public NavigationService(FolderRepository folderRepository, FileRepository fileRepository, UserRepository userRepository, FileDataRepository fileDataRepository) {
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.fileDataRepository = fileDataRepository;
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

    public List<File> getAllFiles(){
        List<File> files = new ArrayList<>();
        for(File file : fileRepository.findAll()){
            files.add(file);
        }
        return files;
    }

    public List<File> getFilesOfUser(int userId){
        User user = userRepository.findById(userId);
        List<File> files = new ArrayList<>();
        for(File file : fileRepository.findAll()){
            if(file.getCreator().equals(user)) {
                files.add(file);
            }
        }
        return files;
    }
}
