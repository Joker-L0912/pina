package com.pina.issue.rest;

import com.pina.auth.entity.PinaUser;
import com.pina.issue.entity.Project;
import com.pina.issue.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public Page<Project> index(String name, Integer pageNum, Integer pageSize, String orderField, String orderType) {
        return projectService.index(name, pageNum, pageSize, orderField, orderType);
    }

    @GetMapping("/{key}")
    public Project getProjectByKey(@PathVariable String key){
        return projectService.getProjectByKey(key);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, String name, String leader, String type, String description){
        projectService.update(id, name, leader, type, description);
    }

    @PostMapping("/user")
    public void addMember(String projectKey, String userId){
        projectService.addMember(projectKey, userId);
    }

    @GetMapping("/user")
    public List<PinaUser> getMember(String projectKey){
        return projectService.getMember(projectKey);
    }

}
