package com.pina.auth.rest;

import com.pina.auth.entity.PinaUser;
import com.pina.auth.service.PinaUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class PinaUserController {

    private final PinaUserService pinaUserService;

    @Autowired
    public PinaUserController(PinaUserService pinaUserService) {
        this.pinaUserService = pinaUserService;
    }

    @PostMapping("/session")
    public String login(String username, String password) {
        String token = pinaUserService.login(username, password);
        return token;
    }

    @GetMapping("/me")
    public PinaUser me(){
        return pinaUserService.me();
    }

    @GetMapping
    public List<PinaUser> index(String name){
        return pinaUserService.index(name);
    }
}
