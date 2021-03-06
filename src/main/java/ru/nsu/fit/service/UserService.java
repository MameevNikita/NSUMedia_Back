package ru.nsu.fit.service;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.fit.database.entities.Token;
import ru.nsu.fit.database.entities.User;
import ru.nsu.fit.database.repositories.TokenRepository;
import ru.nsu.fit.database.repositories.UserRepository;
import ru.nsu.fit.database.types.Role;
import ru.nsu.fit.exception.LogoutException;
import ru.nsu.fit.exception.RegistrationException;
import ru.nsu.fit.web.login.AuthData;
import ru.nsu.fit.web.navigation.UserPublicDTO;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = new BasePasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found.", username));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()))
        );
    }


    public User register(AuthData authData) throws RegistrationException {
        User user = userRepository.findByLogin(authData.getLogin());

        if (user != null) {
            throw new RegistrationException("User with this login already exists");
        }

        User newUser = userRepository.save(new User(authData.getLogin(), passwordEncoder.encode(authData.getPassword()), Role.USER));

        return newUser;
    }

    public Token login(AuthData authData) throws AuthenticationException {
        User user = userRepository.findByLogin(authData.getLogin());

        if (user == null) {
            throw new UsernameNotFoundException("There is no user with same login");
        } else if (!passwordEncoder.matches(authData.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Incorrect login or password");
        }

        String tokenUniqueData = Integer.toString((int) (Math.random() * Integer.MAX_VALUE));
        Token token = tokenRepository.save(new Token(tokenUniqueData, user));
        return token;
    }

    public Token logout(String authToken) throws LogoutException {
        Token token = tokenRepository.findByData(authToken);

        if (token == null) {
            throw new LogoutException("Incorrect token");
        }

        tokenRepository.delete(token);
        return token;
    }

    public User getUser(String authToken) {
        return tokenRepository.findByData(authToken).getOwner();
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            users.add(user);
        }
        return users;
    }

    public User changeRole(UserPublicDTO user) throws UsernameNotFoundException {
        User modifiedUser = userRepository.findByLogin(user.getLogin());

        if (modifiedUser == null) {
            throw new UsernameNotFoundException("There is no user with username " + user.getLogin());
        }

        modifiedUser.setRole(user.getRole());
        modifiedUser = userRepository.save(modifiedUser);

        return modifiedUser;
    }

    public User changePassword(AuthData authData) throws UsernameNotFoundException {
        User modifiedUser = userRepository.findByLogin(authData.getLogin());

        if (modifiedUser == null) {
            throw new UsernameNotFoundException("There is no user with username " + authData.getLogin());
        }

        modifiedUser.setPassword(passwordEncoder.encode(authData.getPassword()));
        modifiedUser = userRepository.save(modifiedUser);

        return modifiedUser;
    }

    @PostConstruct
    private void initializeUsers() {
        User[] administrators = userRepository.findAllByRole(Role.ADMINISTRATOR);

        if (administrators.length == 0) {
            userRepository.save(new User("root", passwordEncoder.encode("root"), Role.ADMINISTRATOR));
        }
    }
}
