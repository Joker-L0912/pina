package com.pina.auth.service.Impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.pina.auth.entity.PinaUser;
import com.pina.auth.repository.PinaUserRepository;
import com.pina.auth.service.PinaUserService;
import com.pina.auth.utils.AuthenticationService;
import com.pina.core.enums.ReturnCode;
import com.pina.core.exception.BaseException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PinaUserServiceImpl implements PinaUserService {

    private final PinaUserRepository pinaUserRepository;
    private final AuthenticationService authenticationService;

    public PinaUserServiceImpl(PinaUserRepository pinaUserRepository, AuthenticationService authenticationService) {
        this.pinaUserRepository = pinaUserRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public String login(String username, String password) {
        Assert.hasText(username, "用户名不能为空");
        Assert.hasText(username, "密码不能为空");

        PinaUser pinaUser = pinaUserRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(ReturnCode.USER_ERROR_A0210));
        String passwordIn = SaSecureUtil.sha256(password);
        if (!passwordIn.equals(pinaUser.getPassword())) {
            throw new BaseException(ReturnCode.USER_ERROR_A0210);
        }
        StpUtil.login(username);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        log.info("{}", tokenInfo);
        return tokenInfo.getTokenValue();
    }

    @Override
    public PinaUser me() {
        String username = StpUtil.getLoginId().toString();
        return pinaUserRepository.findByUsername(username).get();
    }

    @Override
    @Transactional
    public List<PinaUser> index(String name) {
        Pageable pageable = PageRequest.of(0, 100);
        Page<PinaUser> users = pinaUserRepository.findAll(new Specification<PinaUser>() {
            List<Predicate> predicates = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<PinaUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (!StringUtils.isEmpty(name)) {
                    predicates.add(criteriaBuilder.like(root.get("username"), "%" + name + "%"));
                }
                userBaseSpecification(predicates, root, criteriaBuilder);
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        }, pageable);
        return users.getContent();
    }

    private void userBaseSpecification(List<Predicate> predicates, Root<PinaUser> root, CriteriaBuilder criteriaBuilder){
        predicates.add(criteriaBuilder.equal(root.get("status"), "1"));
        predicates.add(criteriaBuilder.equal(root.get("delFlag"), "0"));
        predicates.add(criteriaBuilder.equal(root.get("lesseeId"), authenticationService.getLoginUser().getLesseeId()));
    }
}
