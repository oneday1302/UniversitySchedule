package ua.foxminded.javaspring.universityschedule.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginConntroller {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login_error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
