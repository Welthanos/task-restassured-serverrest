package com.vemser.rest.tests.pojo;

public class LoginResponse {
    private String message;
    private String authorization;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "message='" + message + '\'' +
                ", authorization='" + authorization + '\'' +
                '}';
    }
}
