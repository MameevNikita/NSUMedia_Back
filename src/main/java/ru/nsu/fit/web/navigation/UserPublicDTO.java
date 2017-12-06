package ru.nsu.fit.web.navigation;

import ru.nsu.fit.database.entities.User;
import ru.nsu.fit.database.types.Role;

public class UserPublicDTO {
    private int id;
    private String login;
    private Role role;

    public UserPublicDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.role = user.getRole();
    }

    public String getLogin() {
        return login;
    }

    public Role getRole() {
        return role;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserPublicDTO() {
    }

    public int getId() {
        return id;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
