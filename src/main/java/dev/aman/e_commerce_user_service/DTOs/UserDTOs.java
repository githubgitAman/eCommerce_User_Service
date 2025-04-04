package dev.aman.e_commerce_user_service.DTOs;

import dev.aman.e_commerce_user_service.Models.Roles;
import dev.aman.e_commerce_user_service.Models.User;

import java.util.List;

public class UserDTOs {
    private String name;
    private String email;
    private List<Roles> roles;

    //Converting User to UserDTOs
    public static UserDTOs fromUser(User user) {
        UserDTOs userDTOs = new UserDTOs();
        userDTOs.name = user.getName();
        userDTOs.email = user.getEmail();
        userDTOs.roles = user.getRoles();
        return userDTOs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }
}
