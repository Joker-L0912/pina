package com.pina.issue.repository;

import com.pina.issue.entity.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {
    List<ProjectUser> findByProjectId(Long projectId);

    @Query("select p.projectId from ProjectUser p where p.userId = ?1")
    List<Long> findByUserId(Long userId);
}