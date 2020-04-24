package com.berritus.mis.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2020-04-21
 */
public class JdbcUtil {
	private static final Logger logger = LoggerFactory.getLogger(JdbcUtil.class);

	// 数据库驱动
	public static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	// 数据库连接地址
	public static final String DB_URL = "jdbc:mysql://localhost:3306/spring?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8";
	// 数据库用户名称
	public static final String DB_USER = "root";
	// 数据库用户密码
	public static final String DB_PASSWORD = "lovesnow";

	public static Connection getConnection(String driverClass, String dbUrl,
										   String dbUser, String dbPassword) {
		Connection conn = null;

		try {
			// 加载数据库驱动类
			Class.forName(driverClass);
			logger.info("数据库驱动加载成功");

			// 获取数据库连接对象
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			logger.info("数据库连接成功");

		} catch (ClassNotFoundException cnfe) {
			logger.error("JDBC异常：{}", cnfe);
		} catch (SQLException sqle) {
			logger.error("JDBC异常：{}", sqle);
		} catch (Exception ex) {
			logger.error("JDBC异常：{}", ex);
		}

		return conn;
	}

//	public static Connection getConnection() {
//		Connection conn = null;
//
//		try {
//			// 加载数据库驱动类
//			Class.forName(DRIVER_CLASS);
//			logger.info("数据库驱动加载成功");
//
//			// 获取数据库连接对象
//			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//			logger.info("数据库连接成功");
//
//		} catch (ClassNotFoundException cnfe) {
//			logger.error("JDBC异常：{}", cnfe);
//		} catch (SQLException sqle) {
//			logger.error("JDBC异常：{}", sqle);
//		} catch (Exception ex) {
//			logger.error("JDBC异常：{}", ex);
//		}
//
//		return conn;
//	}

	public static void closeOperate(ResultSet res, Statement stmt, Connection conn) {
		try {
			// 关闭ResultSet对象
			if (res != null) {
				res.close();
			}

			// 关闭Statement对象
			if (stmt != null) {
				stmt.close();
			}

			// 关闭Connection对象
			if (conn != null) {
				conn.close();
			}

			logger.info("关闭数据库操作对象完成");

		} catch (SQLException sqle) {
			logger.error("JDBC异常：{}", sqle);
		}
	}
}
