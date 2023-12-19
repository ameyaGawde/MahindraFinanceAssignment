package com.mtech.assgnproj.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mtech.assgnproj.model.User;

@Service
public class UserService {

	private final PasswordEncoder passwordEncoder;
    private final Map<String, User> users = new HashMap<>();
	Random random = new Random();

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
    	user.setId(random.nextLong() & Long.MAX_VALUE % 900 + 100);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        users.put(user.getEmail(), user);
        return user;
    }

    public User findUserByEmail(String email) {
        return users.get(email);
    }
    
    public User findUserByEmailAndPassword(String email, String password) {
        for (Map.Entry<String, User> entry : users.entrySet()) {
            User user = entry.getValue();
            if (user.getEmail().equals(email) && passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
