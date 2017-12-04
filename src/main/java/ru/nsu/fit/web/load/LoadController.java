package ru.nsu.fit.web.load;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nsu.fit.service.LoadService;
import ru.nsu.fit.service.UserService;
import ru.nsu.fit.web.ErrorMessage;
import ru.nsu.fit.web.navigation.FileDTO;

import java.io.IOException;
import java.sql.Blob;

@RestController
public class LoadController {
    private LoadService loadService;
    private UserService userService;

    public LoadController(LoadService loadService, UserService userService) {
        this.loadService = loadService;
        this.userService = userService;
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    ResponseEntity<?> upload(@RequestBody FileDTO file, @RequestHeader(name = "X-Auth-Token") String authToken){
        FileDTO savedFile;
        try{
            savedFile = new FileDTO(loadService.uploadFile(file, authToken));
        }
        catch(FileUploadException e){
            return ResponseEntity.badRequest().body(new ErrorMessage("Cann't upload file"));
        }

        return ResponseEntity.ok(savedFile);
    }

    @RequestMapping(path = "/uploadData/{id}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> uploadData(@RequestParam("file") MultipartFile file, @PathVariable(name = "id") int fileId){
        FileDTO savedFile;

        try {
            savedFile = new FileDTO(loadService.uploadFileData(fileId, file.getBytes()));
        }
        catch (IOException e){
            return ResponseEntity.badRequest().body(new ErrorMessage("Cann't upload file body"));
        }

        return ResponseEntity.ok(savedFile);
    }
}
