package org.lzq.nyy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Claims;
import org.lzq.nyy.config.MenuConfig;
import org.lzq.nyy.domain.Admins;
import org.lzq.nyy.dto.AdminsDTO;
import org.lzq.nyy.dto.ApiResponse;
import org.lzq.nyy.dto.LoginResponse;
import org.lzq.nyy.service.AdminsService;
import org.lzq.nyy.util.JwtTokenUtil;
import org.lzq.nyy.vo.AdminsVo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class AdminsController {
    @Autowired
    private AdminsService adminsService;

    //加密
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    //解密
    public boolean verifyPassword(String password, String hashedPassword) {return BCrypt.checkpw(password, hashedPassword);}

    @PostMapping("/login")
    public ApiResponse<Object> login(@RequestBody AdminsVo adminsVo) throws JsonProcessingException {
        String email = adminsVo.getEmail();String password = adminsVo.getPasswordHash();
        //Admins admins = adminsService.selectByEmail(email);
        AdminsDTO admins = adminsService.selectAdminsDTO(email);
        ApiResponse<Object> response;
        if (admins != null) {
            String storedHash = admins.getPassword_hash();
            boolean isPasswordMatch = verifyPassword(password, storedHash);
            if (isPasswordMatch) {
                //生成token
                String token = JwtTokenUtil.generateToken(email);
                //获取角色权限ID
                int rolePermissionId = admins.getRole_permission_id();
                //获取菜单详情
                List<Map<String, Object>> menuDetails = MenuConfig.getMenuForRole(rolePermissionId);
                //封装响应数据，包括用户信息与菜单信息
                LoginResponse loginResponse = new LoginResponse(admins, menuDetails);
                response = new ApiResponse<>(200, true, "登录成功", loginResponse, token);
            } else {
                response = new ApiResponse<>(401, false, "密码错误。", null, null);
            }
        } else {
            response = new ApiResponse<>(401, false, "登录失败，请检查您的邮箱和密码。", null, null);
        }
        return response;
    }


    @PostMapping("/register")
    public ApiResponse<Object> register(@RequestBody AdminsVo adminsVo) {
        // 检查必填字段是否为空
        if (adminsVo.getUsername().isEmpty() || adminsVo.getEmail().isEmpty() || adminsVo.getPhoneNumber().isEmpty() || adminsVo.getPasswordHash().isEmpty()) {
            return new ApiResponse<>(400, false, "字段有空", null, null);
        }
        // 检查用户是否已存在
        if (adminsService.selectByEmail(adminsVo.getEmail()) != null) {
            return new ApiResponse<>(400, false, "用户已存在", null, null);
        } else {
            String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String bcryptPassword = hashPassword(adminsVo.getPasswordHash());
            int result = adminsService.insertRegister(adminsVo.getUsername(), adminsVo.getEmail(),
                    adminsVo.getPhoneNumber(), adminsVo.getRolePermissionId(), createdAt, bcryptPassword);
            if (result > 0) {
                return new ApiResponse<>(200, true, "注册成功", null, null);
            } else {
                return new ApiResponse<>(500, false, "注册失败", null, null);
            }
        }
    }

    @GetMapping("/showAllusers")
    public ApiResponse<Object> showAlladmins() {
        //System.out.println(adminsService.showAlladmins());
        return new ApiResponse<>(200, true, "查询全部", adminsService.showAlladmins(), null);
    }

    @PostMapping("/personalInformation")
    public ApiResponse<Object> personalInformation(@RequestBody Admins admins) {
        //System.out.println("这是后端的显示内容:"+adminsService.personalInformation(admins.getEmail()));
        return new ApiResponse<>(200, true, "查询个人信息", adminsService.personalInformation(admins.getEmail()), null);
    }

    @GetMapping("/logs")
    public ApiResponse<Object> getAdminLogs() {
        System.out.println("日志调用");
        List<Map<String, Object>> logs = adminsService.getAllAdminLogs();
        return new ApiResponse<>(200, true, "获取日志成功", logs, null);
    }
}
