package ru.nsu.fit.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.database.entities.File;
import ru.nsu.fit.database.entities.FileData;
import ru.nsu.fit.database.entities.Folder;
import ru.nsu.fit.database.repositories.FileDataRepository;
import ru.nsu.fit.database.repositories.FileRepository;
import ru.nsu.fit.database.repositories.FolderRepository;
import ru.nsu.fit.web.navigation.FileDTO;

@Service
public class FileManagementService {
    private FileRepository fileRepository;
    private FileDataRepository fileDataRepository;
    private FolderRepository folderRepository;

    public FileManagementService(FileRepository fileRepository, FileDataRepository fileDataRepository, FolderRepository folderRepository) {
        this.fileRepository = fileRepository;
        this.fileDataRepository = fileDataRepository;
        this.folderRepository = folderRepository;
    }


    public File markAsChecked(int fileId) {
        File file = fileRepository.findById(fileId);
        file.setChecked(true);
        file = fileRepository.save(file);
        return file;
    }

    public void deleteFile(int fileId){
        File file = fileRepository.findById(fileId);
        Folder parentFolder = file.getParentFolder();

        FileData fileData = fileDataRepository.findByFile(file);
        fileDataRepository.delete(fileData);
        fileRepository.delete(file);

        while(!parentFolder.isRoot() &&
                folderRepository.findAllByParentFolder(parentFolder).length == 0 &&
                fileRepository.findAllByParentFolder(parentFolder).length == 0){
            Folder folder = parentFolder;
            parentFolder = folder.getParentFolder();
            folderRepository.delete(folder);

            System.out.println("Delete folder " + folder.getName());
        }
    }
}
