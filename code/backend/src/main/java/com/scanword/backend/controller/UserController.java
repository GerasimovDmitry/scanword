package com.scanword.backend.controller;

import com.scanword.backend.entity.User;
import com.scanword.backend.entity.models.UserModel;
import com.scanword.backend.entity.models.UserProfile;
import com.scanword.backend.service.UserRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/user")
public class UserController {
    private UserRepositoryService userRepositoryService;

    public UserController(UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @PostMapping("/login")
    public UserProfile login(@RequestBody UserModel userModel) {
        try {
            User user = userRepositoryService.login(userModel);
            UserProfile profile = new UserProfile();
            profile.setLogin(user.getLogin());
            profile.setIsAdmin(user.getIsAdmin());
            return profile;
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Ошибка входа. Проверьте логин/пароль", ex);
        }
    }

    @PostMapping("/registration")
    public UserProfile registration(@RequestBody UserModel userModel) {
        User user = new User();
        try {
            user.setPassword(userModel.getPassword());
            user.setLogin(userModel.getLogin());
            userRepositoryService.saveUser(user);

        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Пользователь с таким логином уже существует");
        }

        UserProfile profile = new UserProfile();
        profile.setLogin(user.getLogin());
        profile.setIsAdmin(user.getIsAdmin());

        return profile;
    }

}
