package com.nc13.spring_legacy.restController;

import com.nc13.spring_legacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class UserRestController {
    @Autowired
    private UserService userService;

    @GetMapping("validateUsername")
    public Map<String, Object> validateUsername(String username) {
        Map<String, Object> resultMap = new HashMap<>();
        boolean result = userService.validateUsername(username);
        System.out.println("username: " + username);
        if (result) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }

        return resultMap;
    }

    @GetMapping("validateNickname")
    public Map<String, Object> validateNickname(String nickname) {
        Map<String, Object> resultMap = new HashMap<>();
        boolean result = userService.validateNickname(nickname);
        System.out.println("nickname: "+nickname);
        if (result) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result","fail");
        }

        return resultMap;
    }
}
