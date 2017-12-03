package ru.nsu.fit.web.navigation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.service.NavigationService;

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

        return ResponseEntity.ok(navigationService.getFolderInfo(folderId));
    }

    @RequestMapping(path = "/navigation/file/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> fileInfo(@PathVariable(name = "id") int fileId) {
        if (!navigationService.containsFile(fileId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(navigationService.getFileInfo(fileId));
    }
}
