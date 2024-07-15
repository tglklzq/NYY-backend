package org.lzq.nyy.service.Impl;

import org.apache.ibatis.session.SqlSession;
import org.lzq.nyy.domain.Admins;
import org.lzq.nyy.dto.AdminsDTO;
import org.lzq.nyy.mapper.AdminLogsMapper;
import org.lzq.nyy.mapper.AdminsMapper;
import org.lzq.nyy.service.AdminsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminsServiceImpl implements AdminsService {
    private static final Logger logger = LoggerFactory.getLogger(AdminsServiceImpl.class);

    private final SqlSession sqlSession;
    final public Integer FinalrolePermissionId = 1; // 默认级别

    @Autowired
    public AdminsServiceImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    // 登录
    @Override
    public Admins selectEmailandPasswordHash(String email, String passwordHash) {
        AdminsMapper mapper = sqlSession.getMapper(AdminsMapper.class);
        return mapper.selectByEmailandPasswordHash(email, passwordHash);
    }

    @Override
    public Admins selectByEmail(String email) {
        AdminsMapper mapper = sqlSession.getMapper(AdminsMapper.class);
        return mapper.selectByEmail(email);
    }

    // 更新
    @Override
    public Admins updateByPrimaryKey(Admins admins) {
        AdminsMapper mapper = sqlSession.getMapper(AdminsMapper.class);
        return mapper.updateByPrimaryKey(admins);
    }

    // 注册
    @Override
    public int insertRegister(String username, String email, String phoneNumber, Integer rolePermissionId, String createdAt, String passwordHash) {
        AdminsMapper adminsMapper = sqlSession.getMapper(AdminsMapper.class);
        rolePermissionId = FinalrolePermissionId;
        return adminsMapper.insertRegister(username, email, phoneNumber, rolePermissionId, createdAt, passwordHash);
    }

    @Override
    public List<AdminsDTO> showAlladmins() {
        AdminsMapper adminsMapper = sqlSession.getMapper(AdminsMapper.class);
        return adminsMapper.showAlladmins();
    }

    // 登录返回用户信息
    @Override
    public AdminsDTO selectAdminsDTO(String email) {
        AdminsMapper adminsMapper = sqlSession.getMapper(AdminsMapper.class);
        return adminsMapper.selectAdminsDTO(email);
    }

    @Override
    public AdminsDTO personalInformation(String email) {
        AdminsMapper adminsMapper = sqlSession.getMapper(AdminsMapper.class);
        return adminsMapper.personalInformation(email);
    }
    @Override
    public List<Map<String, Object>> getAllAdminLogs() {
        AdminLogsMapper adminLogsMapper = sqlSession.getMapper(AdminLogsMapper.class);
        return adminLogsMapper.getAllAdminLogs();
    }

}
