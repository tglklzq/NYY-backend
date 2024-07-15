package org.lzq.nyy.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class LoginResponse {
    private AdminsDTO adminInfo;
    private List<Map<String, Object>> menuDetails;

    public LoginResponse(AdminsDTO adminInfo, List<Map<String, Object>> menuDetails) {
        this.adminInfo = adminInfo;
        this.menuDetails = menuDetails;
    }
}