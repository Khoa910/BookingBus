package com.bookingticket.controller.user;

import com.bookingticket.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class Step2Controller {
    @RequestMapping("/step2")
    public String step2(){
        return "user/step2";
    }
}
