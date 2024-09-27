package com.pina.issue.service;

import com.pina.auth.entity.PinaUser;
import com.pina.issue.entity.Project;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectService {
    Page<Project> index(String name, Integer pageNum, Integer pageSize, String orderField, String orderType);

    Project getProjectByKey(String key);

    void update(Long id, String name, String leader, String type, String description);

    void addMember(String projectKey, String userId);

    List<PinaUser> getMember(String projectKey);
}
