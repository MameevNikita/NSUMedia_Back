package ru.nsu.fit.web.navigation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.database.entities.File;
import ru.nsu.fit.database.entities.Folder;
import ru.nsu.fit.service.NavigationService;

import java.util.stream.Collectors;

@RestController
public class NavigationController {
    private NavigationService navigationService;

    public NavigationController(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    @RequestMapping(path = "/navigation/rootFolder")
    public ResponseEntity<?> rootFolder() {
        return folderInfo(navigationService.getRootFolderId());
    }

    @RequestMapping(path = "/navigation/folder/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> folderInfo(@PathVariable(name = "id") int folderId) {
        if (!navigationService.containsFolder(folderId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(new FolderDTO(navigationService.getFolderInfo(folderId)));
    }

    @RequestMapping(path = "/navigation/file/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> fileInfo(@PathVariable(name = "id") int fileId) {
        if (!navigationService.containsFile(fileId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(new FileDTO(navigationService.getFileInfo(fileId)));
    }

    @RequestMapping(path = "/navigation/filesOfUser/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> filesOfUser(@PathVariable(name = "id") int userId) {
        return ResponseEntity.ok(navigationService.getFilesOfUser(userId).stream().map(FileDTO::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/navigation/allFiles", method = RequestMethod.GET)
    public ResponseEntity<?> allFiles() {
        return ResponseEntity.ok(navigationService.getAllFiles().stream().map(FileDTO::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/navigation/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchFiles(@RequestBody String searchRequest) {
        return ResponseEntity.ok(navigationService.searchFiles(searchRequest).stream().map(FileDTO::new).collect(Collectors.toList()));
    }
}
