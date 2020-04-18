package com.berritus.mis.dao.interceptor;

import com.alibaba.druid.pool.DruidPooledPreparedStatement;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxyImpl;
import com.berritus.mis.dao.school.TbStudentMapper;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * @Copyright:
 * @Description: MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class
 * @Author: Qin Guihe
 * @Create: 2020-04-17
 */
@Intercepts({
		@Signature(
				method = "query",
				type = Executor.class,
				args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
		)
})
//@Component
public class SQLInterceptor implements Interceptor {
	private static final Logger logger= LoggerFactory.getLogger(SQLInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		logger.info("============SQLInterceptor intercept");
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = null;
		if (invocation.getArgs().length > 1) {
			parameter = invocation.getArgs()[1];
		}

		String sqlId = mappedStatement.getId();
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Configuration configuration = mappedStatement.getConfiguration();
		long sqlStartTime = System.currentTimeMillis();
		Object next = invocation.proceed();
		long sqlEndTime = System.currentTimeMillis();
		String sql = getSql(configuration, boundSql, sqlId);
		String sqlTimeLog = sqlId.concat(">>runs :").concat(String.valueOf(sqlEndTime - sqlStartTime)).concat("ms");
		return next;
//		// invocation.getArgs()可以获取到被拦截方法的参数
//		// StatementHandler.update(Statement s)的参数为Statement
//		Statement s = (Statement) invocation.getArgs()[0];
//		// 数据源为DRUID, Statement为DRUID的Statement
//		Statement stmt = ((DruidPooledPreparedStatement) s).getStatement();
//		// 配置druid连接时使用filters: stat配置
//		if (stmt instanceof PreparedStatementProxyImpl) {
//			stmt = ((PreparedStatementProxyImpl) stmt).getRawObject();
//		}
//		// 数据库提供的Statement可获取参数替换后的SQL(JDBC和DRUID获取的是带?的)
//		// 数据库为MySQL,可以直接强制转换为MySQL的PreparedStatement获取SQL
//		// SQL在书写时为了格式容器阅读会有换行符(多个空格)存在
//		// 为了保存和查看方便去除SQL中的换行及多个空格
//		String sql = ((PreparedStatement) stmt).asSql().replaceAll("+", " ");
//		// 保存SQL的操作必须和当前执行的SQL在同一事务中
//		// 使用当前SQL所在的数据库连接执行保存操作即可
//		// 目标sql成功时保存sql的方法也同步成功
//		Connection conn = stmt.getConnection();
//		// 将SQL保存至数据库中
//		PreparedStatement ps = null;
//		try {
//			ps = conn.prepareStatement("select * from sys_tables_map");
//			//ps.setString(1, sql);
//			// 因为和Mybatis的操作在同一事务中
//			// 如果本次操作如果失败, 所有操作都回滚
//			ps.execute();
//		} catch (Exception e) {
//
//		} finally {
//			if (ps != null) {
//				ps.close();
//			}
//		}
//		// 继续执行StatementHandler.update方法
//		return invocation.proceed();
	}

	private String getSql(Configuration configuration, BoundSql boundSql, String sqlId) {
		return sqlId + ">>execute sql:" + assembleSql(configuration, boundSql);
	}

	private String assembleSql(Configuration configuration, BoundSql boundSql) {
		Object sqlParameter = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql().replaceAll("[\\s+]", "").replaceAll("from", "\n\tFROM\n\t").replaceAll("select", "\n\tSELECT\t\n");
		if (parameterMappings.size() > 0 && sqlParameter != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(sqlParameter.getClass())) {
				sql = sql.replaceFirst("\\?", getParameterValue(sqlParameter));
			} else {
				MetaObject metaObject = configuration.newMetaObject(sqlParameter);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					}
				}
			}

		}
		return sql;
	}

	private String getParameterValue(Object obj) {
		String value = "";
		if (obj instanceof String) {
			value = "'".concat(obj.toString()).concat("'");
		} else if (obj instanceof Date) {
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'".concat(dateFormat.format(new Date())) + "'";
		} else {
			if (obj != null) {
				value = obj.toString();
			} else {
				value = "";
			}
		}
		return value != null ? makeStringAllRegExp(value) : value;
	}

	/**
	 * 转义正则特殊字符串
	 *
	 * @param str
	 * @return
	 */
	private String makeStringAllRegExp(String str) {
		if (str != null && !str.equals("")) {
			return str.replace("\\", "\\\\").replace("*", "\\*")
					.replace("+", "\\+").replace("|", "\\|")
					.replace("{", "\\{").replace("}", "\\}")
					.replace("(", "\\(").replace(")", "\\)")
					.replace("^", "\\^").replace("$", "\\$")
					.replace("[", "\\[").replace("]", "\\]")
					.replace("?", "\\?").replace(",", "\\,")
					.replace(".", "\\.").replace("&", "\\&");
		}
		return str;
	}

	@Override
	public Object plugin(Object o) {
		return Plugin.wrap(o,this);
	}

	@Override
	public void setProperties(Properties properties) {
		logger.warn(properties.toString());
	}
}
