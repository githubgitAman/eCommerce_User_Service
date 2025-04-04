package dev.aman.e_commerce_user_service.Services;

import dev.aman.e_commerce_user_service.Models.Tokens;
import dev.aman.e_commerce_user_service.Models.User;

public interface UserService {
    Tokens login(String email, String password);
    User signUp(String name, String email, String password);
    void logout(String token);
    User validateUser(String token);
}
