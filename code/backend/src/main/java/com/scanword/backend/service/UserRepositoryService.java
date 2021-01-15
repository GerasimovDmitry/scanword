package com.scanword.backend.service;

import com.scanword.backend.config.Constants;
import com.scanword.backend.entity.User;
import com.scanword.backend.entity.models.UserModel;
import com.scanword.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Service
public class UserRepositoryService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository repository;
    @Autowired
    public UserRepositoryService(UserRepository repository) {
        this.repository = repository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public User getUserByLogin(String login) {
        User user = repository.getUserByLogin(login);
        System.out.println(user.getLogin());
        return user;
    }

    public void saveUser(User user) throws Exception {
        if(repository.getUserByLogin(user.getLogin()) != null) {
            throw new Exception();
        }
        if (user.getLogin().equals("admin")) {
            user.setIsAdmin(true);
    }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setUuid(Constants.userId);
        repository.save(user);
    }

    public User login(UserModel userModel) throws BadCredentialsException {
        User user = getUserByLogin(userModel.getLogin());
        if (bCryptPasswordEncoder.matches(userModel.getPassword(), user.getPassword())) {
            return user;
        }
        else {
            throw new BadCredentialsException("Неправильный пароль");
        }
    }
}
