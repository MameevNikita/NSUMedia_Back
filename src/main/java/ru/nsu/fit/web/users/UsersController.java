package ru.nsu.fit.web.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.database.entities.User;
import ru.nsu.fit.database.types.Role;
import ru.nsu.fit.service.UserService;
import ru.nsu.fit.web.navigation.UserPublicDTO;

import java.util.stream.Collectors;

@RestController
public class UsersController {
    UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/users/me", method = RequestMethod.GET)
    public ResponseEntity<?> myUserInfo(@RequestHeader(name = "X-Auth-Token") String authToken) {
        return ResponseEntity.ok(new UserPublicDTO(userService.getUser(authToken)));
    }

    @RequestMapping(path = "/users/all", method = RequestMethod.GET)
    public ResponseEntity<?> allUsers() {
        return ResponseEntity.ok(userService.getAllUsers().stream().map(UserPublicDTO::new).collect(Collectors.toList()));
    }


    @RequestMapping(path = "/roles/all", method = RequestMethod.GET)
    public ResponseEntity<?> allRoles() {
        return ResponseEntity.ok(Role.values());
    }

    @RequestMapping(path = "/roles/change", method = RequestMethod.POST)
    public ResponseEntity<?> changeRole(@RequestBody UserPublicDTO user) {

        User modifiedUser = userService.changeRole(user);

        return ResponseEntity.ok(new UserPublicDTO(modifiedUser));
    }
}
