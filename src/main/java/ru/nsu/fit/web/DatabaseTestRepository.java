package ru.nsu.fit.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.database.entities.File;
import ru.nsu.fit.database.entities.Folder;
import ru.nsu.fit.database.entities.User;
import ru.nsu.fit.database.repositories.FileRepository;
import ru.nsu.fit.database.repositories.FolderRepository;
import ru.nsu.fit.database.repositories.UserRepository;

@RestController
public class DatabaseTestRepository {
    private UserRepository userRepository;
    private FolderRepository folderRepository;
    private FileRepository fileRepository;

    public DatabaseTestRepository(UserRepository userRepository, FolderRepository folderRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
    }

    @RequestMapping(path = "/test")
    public ResponseEntity<?> test() {
        userRepository.deleteAll();
        folderRepository.deleteAll();
        fileRepository.deleteAll();

        User rootUser = new User("root", "root");
        User anyUser = new User("any", "any");

        userRepository.save(rootUser);
        userRepository.save(anyUser);

        Folder rootFolder = new Folder("rootFolder", rootUser);
        Folder subFolder = new Folder("subFolder", anyUser, rootFolder);

        folderRepository.save(rootFolder);
        folderRepository.save(subFolder);

        for(int i = 0; i < 5; ++i){
            File file = new File(String.format("File%d", i), anyUser, subFolder, "FIT", "UPPRO", "Lecture", 1990, 3, 4);
            fileRepository.save(file);
        }

        return ResponseEntity.ok(fileRepository.findAll());
    }
}
