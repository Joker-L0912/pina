package com.pina.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "pina_user")
@Entity
public class PinaUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 用户昵称
     */
    @Column(name = "username")
    private String username;

    /**
     * 用户邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 手机号码
     */
    @Column(name = "phonenumber")
    private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    @Column(name = "lessee_id")
    private Long lesseeId;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Column(name = "status")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @Column(name = "del_flag")
    private String delFlag;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
}