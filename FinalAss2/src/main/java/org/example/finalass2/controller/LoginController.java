package org.example.finalass2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html
    }

    @GetMapping("/access-denied")
    public String denied() {
        return "access-denied"; // templates/access-denied.html
    }
}
