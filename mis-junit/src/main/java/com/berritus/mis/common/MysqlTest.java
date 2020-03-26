package com.berritus.mis.common;

import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.controller.conf.MisApplication;
import com.berritus.mis.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: Create in 2020/3/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MisApplication.class)
public class MysqlTest {

    @Autowired
    private DemoService demoService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void callProcedureCreateDealInfoDealInfo(String tableName) {
        jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                String storedProc = "{call createDealInfoXx(?)}";// 调用的sql
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setString(1, tableName);
                //cs.registerOutParameter(9,java.sql.Types.INTEGER);// 注册输出参数 返回类型
                //cs.registerOutParameter(10,java.sql.Types.VARCHAR);// 注册输出参数 返回信息
                return cs;
            }
        }, new CallableStatementCallback() {
            @Override
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                //List<String> result = new ArrayList<String>();
                cs.execute();

                //result.add(cs.getString(9));
                //result.add(cs.getString(10));
                return null;
            }
        });
    }

    @Test
    public void test1(){
        callProcedureCreateDealInfoDealInfo("deall_info_1");
    }
}
