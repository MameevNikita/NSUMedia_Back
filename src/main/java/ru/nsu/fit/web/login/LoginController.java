package ru.nsu.fit.web.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.exception.RegistrationException;
import ru.nsu.fit.service.TokenDTO;
import ru.nsu.fit.service.UserService;
import ru.nsu.fit.web.ErrorMessage;

@RestController
public class LoginController {
    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    ResponseEntity<?> register(@RequestBody AuthData authData){
        UserDTO user;

        try{
            user = userService.register(authData);
        }catch (RegistrationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Cann't register user!"));
        }

        return ResponseEntity.ok(user);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    ResponseEntity<?> login(@RequestBody AuthData authData){
        TokenDTO token;

        try{
            token = userService.login(authData);
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Cann't register user!"));
        }

        return ResponseEntity.ok(token);
    }
}
