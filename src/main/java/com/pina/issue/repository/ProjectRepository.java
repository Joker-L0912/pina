package com.pina.issue.repository;

import com.pina.issue.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @Query("""
            select p from Project p where p.lesseeId = :lesseeId and
            ( :name is null or p.name like %:name%)
            """)
    Page<Project> index(String name, Long lesseeId, Pageable pageable);

    @Query("select p from Project p where p.name like ?1 and p.lesseeId = ?2 and p.id in ?3")
    Page<Project> findByNameLikeAndLesseeIdAndIdIn(@Nullable String name, Long lesseeId, Collection<Long> ids,
                                                   Pageable pageable);

    Optional<Project> findByKeywordAndLesseeId(String key, Long lesseeId);


}
