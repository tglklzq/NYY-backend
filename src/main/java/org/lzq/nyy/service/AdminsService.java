package org.lzq.nyy.service;

import org.lzq.nyy.domain.Admins;
import org.lzq.nyy.dto.AdminsDTO;
import org.lzq.nyy.dto.ApiResponse;

import java.util.List;
import java.util.Map;

public interface AdminsService {
    Admins selectEmailandPasswordHash(String email, String passwordHash);


    int insertRegister(String username, String email, String phoneNumber, Integer rolePermissionId, String createdAt, String passwordHash);

    Admins selectByEmail(String email);

    Admins updateByPrimaryKey(Admins admins);

    List<AdminsDTO> showAlladmins();
    AdminsDTO selectAdminsDTO(String email);
    AdminsDTO personalInformation(String email);

    public List<Map<String, Object>> getAllAdminLogs();
}
