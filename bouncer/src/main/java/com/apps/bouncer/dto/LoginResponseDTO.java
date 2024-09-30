package com.apps.bouncer.dto;

public class LoginResponseDTO {
    private String message;
    private int status;

    public LoginResponseDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
