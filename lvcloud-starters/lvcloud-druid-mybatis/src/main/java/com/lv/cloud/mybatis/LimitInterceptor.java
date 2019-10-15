package com.lv.cloud.mybatis;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.Properties;

/**
 * Mybatis拦截器，用来过滤所有的查询操作，将结果集的数量限制在1001
 *
 */
@Intercepts({
	@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class LimitInterceptor implements Interceptor {
	
	// 结果集的最大数量
	private Integer maxRows = 1001;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getTarget() instanceof StatementHandler) {
			StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
			MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
			MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
			if (mappedStatement != null) {
				// 判断是否是查询语句
				if (SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
					RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
					if (rowBounds == null || (rowBounds.getOffset() == RowBounds.NO_ROW_OFFSET
							&& rowBounds.getLimit() == RowBounds.NO_ROW_LIMIT)) {
						// mybatis的内存分页，重置下面的两个参数
						metaStatementHandler.setValue("delegate.rowBounds.offset", 0);
						// 设置最大返回结果集长度为1001
						metaStatementHandler.setValue("delegate.rowBounds.limit", maxRows);
					}
				}
			}
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties arg0) {

	}

}
