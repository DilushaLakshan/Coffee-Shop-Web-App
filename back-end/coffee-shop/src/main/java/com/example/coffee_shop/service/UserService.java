package com.example.coffee_shop.service;

import com.example.coffee_shop.model.User;
import com.example.coffee_shop.model.UserType;
import com.example.coffee_shop.repository.UserRepository;
import com.example.coffee_shop.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserTypeRepository userTypeRepository;

    public UserService(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }

    public User addUser(User user){
        UserType userType = userTypeRepository.findById(user.getUserTypeId())
                .orElseThrow(() -> new RuntimeException("UserType not found"));

        user.setUserType(userType);

        return userRepository.save(user);
    }
}
