package dev.aman.e_commerce_user_service.Controllers;

import dev.aman.e_commerce_user_service.DTOs.*;
import dev.aman.e_commerce_user_service.DTOs.ResponseStatus;
import dev.aman.e_commerce_user_service.Models.Tokens;
import dev.aman.e_commerce_user_service.Models.User;
import dev.aman.e_commerce_user_service.Services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Post because it will create Token
    @PostMapping
    public LoginResponseDTOs login(@RequestBody RequestBodyDTOs requestBodyDtos){
        LoginResponseDTOs loginResponseDTOs = new LoginResponseDTOs();
        try {
          Tokens token = userService.login(requestBodyDtos.getUserName(), requestBodyDtos.getPassword());
            loginResponseDTOs.setToken(token.getValue());
            loginResponseDTOs.setResponseStatus(ResponseStatus.SUCCESS);
            return loginResponseDTOs;
      }
      catch(Exception e){
          loginResponseDTOs.setResponseStatus(ResponseStatus.FAILURE);
      }
        return loginResponseDTOs;
    }
    @PostMapping
    public UserDTOs signUp(@RequestBody SignUpRequestDTOs signUpRequestDtos){
        User user = userService.signUp(signUpRequestDtos.getUsername(),signUpRequestDtos.getEmail(), signUpRequestDtos.getPassword());
        return UserDTOs.fromUser(user);
    }
    @PatchMapping
    public void logout(@RequestBody LogoutRequestDTOs logoutRequestDtos){

    }
    //Returning whole UserDTOs as we need to verify roles etc
    @GetMapping
    public UserDTOs validateToken(String token){
        return null;
    }
}

