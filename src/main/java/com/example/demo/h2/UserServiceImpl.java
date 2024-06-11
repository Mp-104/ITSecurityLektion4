package com.example.demo.h2;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private H2UserRepository userRepository;


    @Override
    public H2User saveUser(H2User user) {
        return userRepository.save(user);
    }

    @Override
    public List<H2User> fetchUserList() {

        return (List<H2User>) userRepository.findAll();
    }

    @Override
    public H2User updateUser(H2User user, Long id) {

        H2User user2 = userRepository.findById(id).get();

        if (Objects.nonNull(user.getPassword()) && !"".equalsIgnoreCase(user.getPassword())) {
            user2.setUsername(user.getUsername());
        }

        return userRepository.save(user2);
    }

    @Override
    public void deleteUserById(Long id) {

        userRepository.deleteById(id);

    }
}
