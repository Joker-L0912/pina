package com.pina.auth.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.pina.auth.entity.PinaUser;
import com.pina.auth.repository.PinaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationService {

    private final PinaUserRepository userRepository;

    public PinaUser getLoginUser() {
        String username = StpUtil.getLoginId().toString();
        return userRepository.findByUsername(username).get();

    }

    public String getLoginUsername() {
        return StpUtil.getLoginId().toString();
    }
}
