package ru.nsu.fit.web.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.exception.LogoutException;
import ru.nsu.fit.exception.RegistrationException;
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
            user = new UserDTO(userService.register(authData));
        }catch (RegistrationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Cann't register user!"));
        }

        return ResponseEntity.ok(user);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    ResponseEntity<?> login(@RequestBody AuthData authData){
        TokenDTO token;

        try{
            token = new TokenDTO(userService.login(authData));
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Cann't login!"));
        }

        return ResponseEntity.ok(token);
    }

    @RequestMapping(path = "/logoff", method = RequestMethod.GET)
    ResponseEntity<?> logout(@RequestHeader(name = "X-Auth-Token") String authToken){
        TokenDTO token;

        try{
            token = new TokenDTO(userService.logout(authToken));
        }catch (LogoutException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Cann't logout!"));
        }

        return ResponseEntity.ok(token);
    }
}
