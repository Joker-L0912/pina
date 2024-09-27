package com.pina.issue.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@Table(name = "pina_project")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "leader")
    private String leader;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "lessee_id")
    private Long lesseeId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}
