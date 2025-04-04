package dev.aman.e_commerce_user_service.DTOs;

import dev.aman.e_commerce_user_service.Models.Tokens;

public class LogoutRequestDTOs {
    //Returning only String in token not the whole token
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
