package com.nc13.spring_legacy.service;

import com.nc13.spring_legacy.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDetailService implements UserDetailsService {
    private UserService userService;

    @Autowired
    public CustomDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username: " + username);
        System.out.println("userService: " + userService);
        UserDTO userDTO = userService.selectOne(username);
        System.out.println("userDTO: " + userDTO);
        return userService.selectOne(username);
    }
}
