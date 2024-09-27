package com.pina.issue.entity;

import com.pina.auth.entity.PinaUser;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Table(name = "pina_project_user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "user_id")
    private Long userId;

    @Transient
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private PinaUser user;
}
