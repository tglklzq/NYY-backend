package org.lzq.nyy.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Admins {
    /**
     * 用户ID
     */
    @TableId(value = "admin_id", type = IdType.AUTO)
    private Integer adminId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码哈希
     */
    private String passwordHash;

    /**
     * 电子邮件地址
     */
    private String email;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 角色权限ID
     */
    private Integer rolePermissionId;

    /**
     * 删除标识
     */
    private Boolean isDeleted;

    /**
     * 注销时间
     */
    private Date deletedAt;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}