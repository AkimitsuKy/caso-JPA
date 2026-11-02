package com.example.models;

/**
 * Credenciales recibidas por el servicio de log-in.
 */
public class LoginDTO {

    private String address;

    private String password;

    public LoginDTO() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
