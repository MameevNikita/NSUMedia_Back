package ru.nsu.fit.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.database.entities.File;
import ru.nsu.fit.database.entities.Folder;
import ru.nsu.fit.database.repositories.FileRepository;
import ru.nsu.fit.database.repositories.FolderRepository;
import ru.nsu.fit.web.navigation.FileDTO;
import ru.nsu.fit.web.navigation.FolderDTO;

import javax.annotation.PostConstruct;

@Service
public class NavigationService {
    private int rootFolderId;

    private FolderRepository folderRepository;
    private FileRepository fileRepository;

    public NavigationService(FolderRepository folderRepository, FileRepository fileRepository) {
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
    }

    @PostConstruct
    private void initializeFolders() {
        Folder rootFolder = folderRepository.findByRoot(true);

        if (rootFolder == null) {
            rootFolder = folderRepository.save(new Folder("root", null, true));
        }

        rootFolderId = rootFolder.getId();

        // Todo: remove! Only for testing.

        Folder a1 = new Folder("a1", null, false);
        a1 = folderRepository.save(a1);
        a1.setParentFolder(rootFolder);
        folderRepository.save(a1);

        Folder a2 = new Folder("a2", null, false);
        a2 = folderRepository.save(a2);
        a2.setParentFolder(rootFolder);
        folderRepository.save(a2);

        Folder a3 = new Folder("a3", null, false);
        a3 = folderRepository.save(a3);
        a3.setParentFolder(rootFolder);
        folderRepository.save(a3);


        Folder b1 = new Folder("b1", null, false);
        b1 = folderRepository.save(b1);
        b1.setParentFolder(a1);
        folderRepository.save(b1);

        File f1 = new File("File", null, null, "FIT", "UPPRO", "Lecture", 1999, 3, 4);
        f1 = fileRepository.save(f1);
        f1.setParentFolder(b1);
        fileRepository.save(f1);
    }

    public int getRootFolderId() {
        return rootFolderId;
    }

    public boolean containsFolder(int folderId) {
        return folderRepository.findById(folderId) != null;
    }

    public FolderDTO getFolderInfo(int folderId) {
        return new FolderDTO(folderRepository.findById(folderId));
    }

    public boolean containsFile(int fileId) {
        return fileRepository.findById(fileId) != null;
    }

    public FileDTO getFileInfo(int fileId) {
        return new FileDTO(fileRepository.findById(fileId));
    }
}
