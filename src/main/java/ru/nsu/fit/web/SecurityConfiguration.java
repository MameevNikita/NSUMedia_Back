package ru.nsu.fit.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.nsu.fit.database.repositories.TokenRepository;
import ru.nsu.fit.database.repositories.UserRepository;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private TokenRepository tokenRepository;
    private UserRepository userRepository;

    public SecurityConfiguration(TokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        final TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter(tokenRepository, userRepository);
        http.addFilterBefore(tokenFilter, BasicAuthenticationFilter.class);


        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/navigation/**").permitAll()
                .antMatchers("/login", "/register").anonymous()
                .antMatchers("/logoff", "/upload", "/uploadData/**", "/users/me", "/management/deleteFile/**").authenticated()
                .antMatchers("/roles/change", "/management/markFileAsChecked/**").hasAuthority("ADMINISTRATOR")
                .anyRequest().permitAll()
        .and()
                .csrf().disable();
    }
}
