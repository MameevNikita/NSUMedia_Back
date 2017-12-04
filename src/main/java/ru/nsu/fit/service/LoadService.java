package ru.nsu.fit.service;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import ru.nsu.fit.database.entities.File;
import ru.nsu.fit.database.entities.FileData;
import ru.nsu.fit.database.entities.Folder;
import ru.nsu.fit.database.entities.User;
import ru.nsu.fit.database.repositories.FileDataRepository;
import ru.nsu.fit.web.login.UserDTO;
import ru.nsu.fit.web.navigation.FileDTO;
import ru.nsu.fit.web.navigation.FolderDTO;

import java.util.Arrays;
import java.util.List;

@Service
public class LoadService {
    private NavigationService navigationService;
    private UserService userService;
    private FileDataRepository fileDataRepository;

    public LoadService(NavigationService navigationService, UserService userService, FileDataRepository fileDataRepository) {
        this.navigationService = navigationService;
        this.userService = userService;
        this.fileDataRepository = fileDataRepository;
    }

    public File uploadFile(FileDTO fileInfo, String authToken) throws FileUploadException {
        User creator = userService.getUser(authToken);
        List<String> foldersPath = Arrays.asList(fileInfo.getFaculty(), fileInfo.getDiscipline(), fileInfo.getDocumentType(), creator.getLogin());

        Folder lastFolder = navigationService.createPath(foldersPath, creator);
        File file = navigationService.createFile(fileInfo, lastFolder, creator);

        return file;
    }

    public File uploadFileData(int fileId, byte[] data){
        File file = navigationService.getFileInfo(fileId);
        fileDataRepository.save(new FileData(file, data));
        return file;
    }
}
