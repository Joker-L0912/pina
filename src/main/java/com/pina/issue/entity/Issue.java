package com.pina.issue.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@ToString(callSuper = true)
@Getter
@Setter
@Builder
@Entity
@Table(name = "pina_issue")
@Comment("问题表")
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    @Id
    @Column(columnDefinition = "INT UNSIGNED COMMENT 'ID'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50) COMMENT '关键字'", nullable = false)
    private String name;

    /**
     * 摘要
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '摘要'", nullable = false)
    private String gist;

    @Column(name = "project_id")
    private Long projectId;

    /**
     * 类型
     */
//    @ManyToOne(targetEntity = IssueType.class)
//    @JoinColumn(name = "type_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none", value =
//            ConstraintMode.NO_CONSTRAINT))
//    private IssueType issueType;

    /**
     * 项目
     */
    @Transient
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    /**
     * 描述
     */
    @Column(columnDefinition = "VARCHAR(1000) COMMENT '描述'")
    private String description;

    /**
     * 优先级
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '问题优先级'")
    private String priority;
//    private Integer priorityId;

    /**
     * 状态
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '问题状态'")
    private String issueStatus;

    /**
     * 解决结果
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '解决结果'")
    private String solutionResult;

    /**
     * 报告人
     */
    private String reportedBy;

    /**
     * 经办人
     */
    private String handledBy;

    /**
     * 开发人员
     */
    private String developedBy;

    /**
     * 测试人员
     */
    private String testedBy;
}
