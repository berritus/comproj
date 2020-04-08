package com.berritus.mis.service.base;

import com.berritus.mis.service.impl.MessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: Create in 2020/3/26
 */
@Service
public class CallProcedureService {
    private static final Logger logger = LoggerFactory.getLogger(CallProcedureService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void callProcedureCreateMqMessageInfoXx(String tableName, String sysCode) {
        jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                // 调用的sql
                String storedProc = "{call createMqMessageInfoXx(?)}";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setString(1, tableName);
                return cs;
            }
        }, new CallableStatementCallback() {
            @Override
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                cs.execute();
                logger.info("调用存储过程新建表{},sysCode={}", tableName, sysCode);
                return null;
            }
        });
    }

    public void callProcedureCreateStudentXx(String tableName, String sysCode) {
        jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                // 调用的sql
                String storedProc = "{call createStudentXx(?)}";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setString(1, tableName);
                return cs;
            }
        }, new CallableStatementCallback() {
            @Override
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                cs.execute();
                logger.info("调用存储过程新建表{},sysCode={}", tableName, sysCode);
                return null;
            }
        });
    }
}
