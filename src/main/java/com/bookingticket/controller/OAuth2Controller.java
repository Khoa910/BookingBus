package com.bookingticket.controller;

import ch.qos.logback.core.model.Model;
import com.bookingticket.dto.request.UserRequest;
import com.bookingticket.dto.request.UserRequestOAuth;
import com.bookingticket.entity.Role;
import com.bookingticket.entity.User;
import com.bookingticket.mapper.UserMapper;
import com.bookingticket.repository.RoleRepository;
import com.bookingticket.repository.UserRepository;
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
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    @GetMapping("/callback/google")
    public String handleGoogleLogin(OAuth2AuthenticationToken authentication, HttpSession session) {
        System.out.println("Bắt đầu xử lý đăng nhập Google");
        if (authentication == null) {
            System.out.println("Authentication null");
            return "redirect:/error";
        }

        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
        if (attributes.get("sub") == null) {
            if((!userRepository.existsById(Long.parseLong((String) attributes.get("id"))))
                    &&(!userRepository.existsByUsername(attributes.get("name").toString()))) {
                UserRequestOAuth userRequest = new UserRequestOAuth();
                userRequest.setID(Long.parseLong((String) attributes.get("id")));
                userRequest.setEmail(attributes.get("email").toString());
                userRequest.setPassword("OAuth");
                userRequest.setFull_name(attributes.get("name").toString());
                userRequest.setUsername(attributes.get("name").toString());
                userRequest.setPhone_number("1234567890");
                userRequest.setRole(1L);
                userRequest.setAddress("TPHCM");
                User user = userMapper.toEntity(userRequest);
                userRepository.save(user);
            }
            Long Id =userRepository.findUserByUsername(attributes.get("name").toString()).getId();
            session.setAttribute("id", Id);
            session.setAttribute("username", attributes.get("name"));
            session.setAttribute("email", attributes.get("email"));
            System.out.println("User info: " + attributes);
            return "redirect:/index";
        }
            if((!userRepository.existsById(Long.parseLong(((String) attributes.get("sub")).substring(0, 17))))
                    &&(!userRepository.existsByUsername(attributes.get("name").toString()))){
                UserRequestOAuth userRequest = new UserRequestOAuth();
                userRequest.setID(Long.parseLong(((String) attributes.get("sub")).substring(0, 19)));
                userRequest.setEmail(attributes.get("email").toString());
                userRequest.setPassword("OAuth");
                userRequest.setFull_name(attributes.get("name").toString());
                userRequest.setUsername(attributes.get("name").toString());
                userRequest.setPhone_number("1234567890");
                userRequest.setRole(1L);
                userRequest.setAddress("TPHCM");
                User user = userMapper.toEntity(userRequest);
                userRepository.save(user);
            }
            Long Id =userRepository.findUserByUsername(attributes.get("name").toString()).getId();
            session.setAttribute("id", Id);
            session.setAttribute("username", attributes.get("name"));
            session.setAttribute("email", attributes.get("email"));
            System.out.println("User info: " + attributes);
        return "redirect:/index";
    }
}