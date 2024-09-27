package com.pina.auth.service;

import com.pina.auth.entity.PinaUser;

import java.util.List;

public interface PinaUserService {
    String login(String username, String password);

    PinaUser me();

    List<PinaUser> index(String name);
}
