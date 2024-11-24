package com.bookingticket.controller;

import ch.qos.logback.core.model.Model;
import com.bookingticket.entity.Role;
import com.bookingticket.repository.RoleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/oauth2")
public class OAuth2Controller {
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/callback/google")
    public String handleGoogleLogin(OAuth2AuthenticationToken authentication, HttpSession session) {
        System.out.println("Bắt đầu xử lý đăng nhập Google");
        if (authentication == null) {
            System.out.println("Authentication null");
            return "redirect:/error";
        }

        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
        session.setAttribute("username", attributes.get("name"));
        session.setAttribute("email", attributes.get("email"));
        System.out.println("User info: " + attributes);

        return "redirect:/index";
    }
}