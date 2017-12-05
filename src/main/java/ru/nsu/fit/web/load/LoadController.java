package ru.nsu.fit.web.load;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nsu.fit.database.entities.File;
import ru.nsu.fit.service.LoadService;
import ru.nsu.fit.service.NavigationService;
import ru.nsu.fit.web.ErrorMessage;
import ru.nsu.fit.web.navigation.FileDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LoadController {
    private LoadService loadService;
    private NavigationService navigationService;

    public LoadController(LoadService loadService, NavigationService navigationService) {
        this.loadService = loadService;
        this.navigationService = navigationService;
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    ResponseEntity<?> upload(@RequestBody FileDTO file, @RequestHeader(name = "X-Auth-Token") String authToken) {
        FileDTO savedFile;
        try {
            savedFile = new FileDTO(loadService.uploadFile(file, authToken));
        } catch (FileUploadException e) {
            return ResponseEntity.badRequest().body(new ErrorMessage("Cann't upload file"));
        }

        return ResponseEntity.ok(savedFile);
    }

    @RequestMapping(path = "/uploadData/{id}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> uploadData(@RequestParam("file") MultipartFile file, @PathVariable(name = "id") int fileId) {
        FileDTO savedFile;

        try {
            savedFile = new FileDTO(loadService.uploadFileData(fileId, file.getBytes()));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new ErrorMessage("Cann't upload file body"));
        }

        return ResponseEntity.ok(savedFile);
    }

    @RequestMapping("/download/{id}")
    public void download(HttpServletRequest request,
                         HttpServletResponse response,
                         @PathVariable(name = "id") int fileId) {

        File file = navigationService.getFileInfo(fileId);
        response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());
        try {
            response.getOutputStream().write(loadService.getFileData(file));
            response.getOutputStream().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
