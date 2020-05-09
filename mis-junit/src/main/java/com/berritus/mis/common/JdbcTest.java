package com.berritus.mis.common;

import java.sql.*;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2020-04-13
 */
public class JdbcTest {
	// 数据库驱动
//	public static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
//	// 数据库连接地址
//	public static final String DB_URL = "jdbc:mysql://localhost:3306/spring?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8";
//	// 数据库用户名称
//	public static final String DB_USER = "root";
//	// 数据库用户密码
//	public static final String DB_PASSWORD = "lovesnow";

//	public static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
//	// 数据库连接地址
//	public static final String DB_URL = "jdbc:oracle:thin:@172.16.2.91:1521:lzoa";
//	// 数据库用户名称
//	public static final String DB_USER = "oa_xxzx";
//	// 数据库用户密码
//	public static final String DB_PASSWORD = "xxzx123456";

	public static final String DRIVER_CLASS = "dm.jdbc.driver.DmDriver";
	// 数据库连接地址
	public static final String DB_URL = "jdbc:dm://localhost:5236/DMSERVER";
	// 数据库用户名称
	public static final String DB_USER = "SYSDBA";
	// 数据库用户密码
	public static final String DB_PASSWORD = "lovesnow@";

	public static Connection getConnection() {
		Connection conn = null;

		try {
			// 加载数据库驱动类
			Class.forName(DRIVER_CLASS);
			System.out.println("数据库驱动加载成功");

			// 获取数据库连接对象
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			System.out.println("数据库连接成功");

		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return conn;
	}

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

			System.out.println("关闭数据库操作对象完成");

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}


	public static void execProcedure() {
		Connection conn = null; // 数据库连接对象
		CallableStatement clbStmt = null; // CallableStatement对象
		ResultSet res = null; // 结果集对象
		try {
			String msg = "";
			// 获取数据库连接
			conn = getConnection();

			// 创建CallableStatement对象
			clbStmt = conn.prepareCall("{CALL createStudentXx(?,?)}");

			// 设置输入参数
			clbStmt.setString(1, "tb_student2"); // 查询第3页数据
			clbStmt.setString(1, msg); // 查询第3页数据
//			clbStmt.setInt(2, 10); // 每页10条数据
//
//			// 注册输出参数
//			clbStmt.registerOutParameter(3, Types.INTEGER);
//			clbStmt.registerOutParameter(4, Types.INTEGER);

			// 执行调用存储过程，并获取结果集
			res = clbStmt.executeQuery();

			// 循环遍历结果集
			while (res.next()) {
				// 获取列值
//				int id = res.getInt("id");
//				String name = res.getString("name");
//				Timestamp createTime = res.getTimestamp("create_time");
//
//				// 输出列值
//				System.out.println("编号：" + id + "  姓名：" + name + "  创建时间：" + createTime);
			}

			// 获取输出参数值
//			int totalCount = clbStmt.getInt(3);
//			int totalPage = clbStmt.getInt(4);
//			System.out.println("数据总数：" + totalCount + " 总页数：" + totalPage);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			// 关闭数据库操作对象
			closeOperate(res, clbStmt, conn);
		}
	}

	public static void executeSql(String sql) throws Exception {
		// 获取数据库连接
		Connection conn = getConnection();
		// 使用Connection来创建一个Statement对象
		Statement stmt = conn.createStatement();
		String sql1 = "update tb_student set stu_name= ? where id=?";
		PreparedStatement pstm = conn.prepareStatement(sql1);
		String deal_comm = "hello dm";
		pstm.setString(1, deal_comm);
		pstm.setString(2, "4");
		// 执行SQL,返回boolean值表示是否包含ResultSet
		pstm.executeUpdate();

//		String sql2 = "update deal_info set hq_deal_comm= ? where flow_inid=?";
//		pstm = conn.prepareStatement(sql2);
//		String deal_comm2 = "dddd";
//		pstm.setString(1, deal_comm2);
//		pstm.setString(2, "19768");
//		// 执行SQL,返回boolean值表示是否包含ResultSet
//		pstm.executeUpdate();

		try {
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//boolean hasResultSet = stmt.execute(sql);
		// 如果执行后有ResultSet结果集
//		if (hasResultSet) {
//			try (
//					// 获取结果集
//					ResultSet rs = stmt.getResultSet()) {
//				// ResultSetMetaData是用于分析结果集的元数据接口
//				ResultSetMetaData rsmd = rs.getMetaData();
//				int columnCount = rsmd.getColumnCount();
//				// 迭代输出ResultSet对象
//				while (rs.next()) {
//					// 依次输出每列的值
//					for (int i = 0; i < columnCount; i++) {
//						System.out.print(rs.getString(i + 1) + "\t");
//					}
//					System.out.print("\n");
//				}
//			}
//		} else {
//			System.out.println("该SQL语句影响的记录有"
//					+ stmt.getUpdateCount() + "条");
//		}
	}

	public static void main(String[] args) throws Exception{
		//execProcedure();
		String sql = "update deal_info set deal_comm= ? where flow_inid='19768'";
		executeSql(sql);
	}
}
