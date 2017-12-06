package ru.nsu.fit.web.fileManagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.database.entities.File;
import ru.nsu.fit.database.entities.User;
import ru.nsu.fit.database.types.Role;
import ru.nsu.fit.service.FileManagementService;
import ru.nsu.fit.service.NavigationService;
import ru.nsu.fit.service.UserService;
import ru.nsu.fit.web.ErrorMessage;
import ru.nsu.fit.web.InformationMessage;
import ru.nsu.fit.web.navigation.FileDTO;
import ru.nsu.fit.web.navigation.UserPublicDTO;

import java.util.stream.Collectors;

@RestController
public class FileManagementController {
    private FileManagementService fileManagementService;
    private UserService userService;
    private NavigationService navigationService;

    public FileManagementController(FileManagementService fileManagementService, UserService userService, NavigationService navigationService) {
        this.fileManagementService = fileManagementService;
        this.userService = userService;
        this.navigationService = navigationService;
    }

    @RequestMapping(path = "/management/deleteFile/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteFile(@RequestHeader(name = "X-Auth-Token") String authToken, @PathVariable(name = "id") int fileId) {
        User user = userService.getUser(authToken);
        File file = navigationService.getFileInfo(fileId);

        if(!(user.getRole() == Role.ADMINISTRATOR || file.getCreator().getLogin().equals(user.getLogin()))){
            return ResponseEntity.badRequest().body(new ErrorMessage("You have no permissions to delete this file!"));
        }

        fileManagementService.deleteFile(fileId);
        return ResponseEntity.ok(new InformationMessage("File deleted"));
    }

    @RequestMapping(path = "/management/markFileAsChecked/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> markFileAsChecked(@PathVariable(name = "id") int fileId) {
        File file = fileManagementService.markAsChecked(fileId);
        return ResponseEntity.ok(new FileDTO(file));
    }
}
