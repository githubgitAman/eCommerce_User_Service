package dev.aman.e_commerce_user_service.DTOs;



public class LoginResponseDTOs {
    //Returning only String in token not the whole token
    private String token;
    private ResponseStatus responseStatus;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
