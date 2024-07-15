package org.lzq.nyy.mapper;

import java.util.List;
import java.util.Map;

public interface AdminLogsMapper {
    void insertAdminLog(int adminId, String action, String details);
    List<Map<String, Object>> getAllAdminLogs();
}
