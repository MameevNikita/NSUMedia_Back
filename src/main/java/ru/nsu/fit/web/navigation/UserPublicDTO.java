package ru.nsu.fit.web.navigation;

import ru.nsu.fit.database.entities.User;

public class UserPublicDTO {
    private String login;

    public UserPublicDTO(User user) {
        this.login = user.getLogin();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
