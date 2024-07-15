package org.lzq.nyy.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AdminsDTO {
//    private String username;
//    private String email;
//    private String role_name;
//    private int role_permission_id;
//    private String phone_number;
//    private String password_hash;
    private Integer admin_id;
    private String username;
    private String password_hash;
    private String email;
    private String phone_number;
    private Integer role_permission_id;
    private Boolean isDeleted;
    private Date deletedAt;
    private Date createdAt;
    private Date updatedAt;
    private String role_name;
    private String permission_name;

}
