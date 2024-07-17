package com.nc13.spring_legacy.controller;

import com.nc13.spring_legacy.model.BoardDTO;
import com.nc13.spring_legacy.model.UserDTO;
import com.nc13.spring_legacy.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String showHome(Model model) {
        model.addAttribute("user","princessClub");
        return "index";
    }

}
