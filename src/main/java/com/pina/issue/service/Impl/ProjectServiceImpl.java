package com.pina.issue.service.Impl;

import com.pina.auth.entity.PinaUser;
import com.pina.auth.utils.AuthenticationService;
import com.pina.core.exception.BaseException;
import com.pina.issue.entity.Project;
import com.pina.issue.entity.ProjectUser;
import com.pina.issue.repository.ProjectRepository;
import com.pina.issue.repository.ProjectUserRepository;
import com.pina.issue.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final AuthenticationService authenticationService;

    private final ProjectUserRepository projectUserRepository;

    @Override
    public Page<Project> index(String name, Integer pageNum, Integer pageSize, String orderField, String orderType) {

        PinaUser user = authenticationService.getLoginUser();
        if (StringUtils.isEmpty(orderField)) {
            orderField = "createTime";
        }
        Sort sort;
        if (StringUtils.equals("ASC", orderType)) {
            sort = Sort.by(Sort.Direction.ASC, orderField);
        } else {
            sort = Sort.by(Sort.Direction.DESC, orderField);
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        List<Long> projectIds = projectUserRepository.findByUserId(user.getId());
        Page<Project> projects = projectRepository.findByNameLikeAndLesseeIdAndIdIn("%" + name + "%", user.getId(),
                projectIds, pageable);
        return projects;
    }

    @Override
    @Transactional
    public Project getProjectByKey(String key) {
        Assert.hasText(key, "请输入项目对应关键字");
        PinaUser user = authenticationService.getLoginUser();
        Project project = getProjectByKeyAndLessee(key, user.getLesseeId());
        return project;
    }

    private Project getProjectByKeyAndLessee(String key, Long lesseeId) {
        return projectRepository.findByKeywordAndLesseeId(key, lesseeId).orElseThrow(() -> new BaseException(
                "项目不存在"));
    }

    @Override
    @Transactional
    public void update(Long id, String name, String leader, String type, String description) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new BaseException("项目不存在"));

        project.setName(name);
        project.setLeader(leader);
        project.setType(type);
        project.setDescription(description);

        projectRepository.save(project);
    }

    @Override
    @Transactional
    public void addMember(String projectKey, String userId) {
        String[] userIds = userId.split(",");
        Project project = getProjectByKey(projectKey);
        List<ProjectUser> projectUsers = new ArrayList<>();
        for (String id : userIds) {
            Long idLong = Long.valueOf(id);
            ProjectUser projectUser = ProjectUser.builder()
                    .projectId(project.getId())
                    .userId(idLong)
                    .build();
            projectUsers.add(projectUser);
        }
        projectUserRepository.saveAll(projectUsers);
    }

    @Override
    @Transactional
    public List<PinaUser> getMember(String projectKey) {
        PinaUser user = authenticationService.getLoginUser();
        Project project = getProjectByKeyAndLessee(projectKey, user.getLesseeId());
        List<ProjectUser> projectUsers = projectUserRepository.findByProjectId(project.getId());
        List<PinaUser> users = projectUsers.stream().map(ProjectUser::getUser).toList();
        return users;
    }
}
