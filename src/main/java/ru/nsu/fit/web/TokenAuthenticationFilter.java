package ru.nsu.fit.web;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import ru.nsu.fit.database.entities.Token;
import ru.nsu.fit.database.entities.User;
import ru.nsu.fit.database.repositories.TokenRepository;
import ru.nsu.fit.database.repositories.UserRepository;
import ru.nsu.fit.database.types.Role;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

public class TokenAuthenticationFilter extends GenericFilterBean {

    private TokenRepository tokenRepository;
    private UserRepository userRepository;

    public TokenAuthenticationFilter(TokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        //extract token from header
        final String accessToken = httpRequest.getHeader("X-Auth-Token");
        if (null != accessToken) {
            Token token = tokenRepository.findByData(accessToken);

            if(null != token) {

                User owner = token.getOwner();
                System.out.println("Request: accessToken = " + accessToken + " :: owner = " + owner.getLogin());
                Role role = owner.getRole();

                    //Populate SecurityContextHolder by fetching relevant information using token
                    final org.springframework.security.core.userdetails.User user =
                            new org.springframework.security.core.userdetails.User(
                                owner.getLogin(),
                                owner.getPassword(),
                                true,
                                true,
                                true,
                                true,
                                Collections.singletonList(new SimpleGrantedAuthority(role.toString())));

                    final UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}