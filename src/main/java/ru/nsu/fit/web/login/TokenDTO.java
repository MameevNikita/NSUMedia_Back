package ru.nsu.fit.web.login;

public class TokenDTO {
    private String data;

    public TokenDTO() {
    }

    public TokenDTO(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
