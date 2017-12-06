package ru.nsu.fit.web.login;

import ru.nsu.fit.database.entities.User;
import ru.nsu.fit.database.types.Role;

public class UserDTO {
    private int id;
    private String login;
    private String passwordHash;
    private Role role;

    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.passwordHash = user.getPassword();
        this.role = user.getRole();
    }

    public int getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
