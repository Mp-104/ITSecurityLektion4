package com.example.demo.h2;

import java.util.List;

public interface UserService {

    H2User saveUser (H2User user);

    List<H2User> fetchUserList ();

    H2User updateUser (H2User user, Long id);

    void deleteUserById (Long id);

}
