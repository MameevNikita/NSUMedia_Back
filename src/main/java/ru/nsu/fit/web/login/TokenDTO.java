package ru.nsu.fit.web.login;

import ru.nsu.fit.database.entities.Token;

public class TokenDTO {
    private String data;

    public TokenDTO() {
    }

    public TokenDTO(Token token) {
        this.data = token.getData();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
