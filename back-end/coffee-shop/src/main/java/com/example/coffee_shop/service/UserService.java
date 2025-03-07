package com.example.coffee_shop.service;

import com.example.coffee_shop.model.User;
import com.example.coffee_shop.model.UserType;
import com.example.coffee_shop.repository.UserRepository;
import com.example.coffee_shop.repository.UserTypeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserTypeRepository userTypeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public User addUser(User user){
//        UserType userType = userTypeRepository.findById(user.getUserTypeId())
//                .orElseThrow(() -> new RuntimeException("UserType not found"));
//
//        user.setUserType(userType);
//
//        return userRepository.save(user);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user.get();
    }

    public User registerUser(String name, String email, String address, String contact, String password, long userTypeId) {
        UserType userType = userTypeRepository.findById(userTypeId)
                .orElseThrow(() -> new RuntimeException("UserType not found"));

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAddress(address);
        user.setContact(contact);
        user.setPassword(passwordEncoder.encode(password)); // Hash the password
        user.setUserType(userType);

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByEmail(username);
    }
}
