package ru.nsu.fit.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.fit.database.entities.User;
import ru.nsu.fit.database.repositories.TokenRepository;
import ru.nsu.fit.database.repositories.UserRepository;
import ru.nsu.fit.database.types.Role;
import ru.nsu.fit.exception.RegistrationException;
import ru.nsu.fit.web.login.AuthData;
import ru.nsu.fit.web.login.UserDTO;

import java.util.Collections;

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


    public UserDTO register(AuthData authData) throws RegistrationException {
        User user = userRepository.findByLogin(authData.getLogin());

        if(user != null){
            throw new RegistrationException("User with this login already exists");
        }

        User newUser = userRepository.save(new User(authData.getLogin(), passwordEncoder.encode(authData.getPassword()), Role.USER));

        return new UserDTO(newUser.getLogin(), newUser.getPassword(), newUser.getRole());
    }
}
