package com.examw.netplatform.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.log4j.Logger;

/**
 * mybatis-mysql 通用分页拦截器。
 * 
 * @author jeasonyoung
 * @since 2015年8月10日
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}),
					 @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class PagingHelper implements Interceptor {
	private static final Logger logger = Logger.getLogger(PagingHelper.class);
	private static final ThreadLocal<Page<?>> localPage = new ThreadLocal<Page<?>>();
	/**
	 * 开始分页。
	 * @param pageIndex
	 * 页码。
	 * @param pageSize
	 * 页数据。
	 */
	public static <E> void startPage(int pageIndex, int pageSize){
		logger.debug("分页["+pageIndex+","+pageSize+"]...");
		localPage.set(new Page<E>(pageIndex, pageSize));
	}
	/**
	 * 结束分页并返回结果。
	 * @return
	 */
	public static Page<?> endPage(){
		logger.debug("结束分页，并返回结果...");
		Page<?> page = localPage.get();
		localPage.remove();
		return page;
	}
	/*
	 * 拦截器处理。
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		logger.debug("拦截器处理...");
		if(localPage.get() == null){
			return invocation.proceed();
		}
		if(invocation.getTarget() instanceof StatementHandler){
			logger.debug("拦截StatementHandler处理...");
			StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
			MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
			//分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
			while(metaStatementHandler.hasGetter("h")){
				Object object = metaStatementHandler.getValue("h");
				metaStatementHandler = SystemMetaObject.forObject(object);
			}
			//分离最后一个代理对象的目标类
			while(metaStatementHandler.hasGetter("target")){
				Object object = metaStatementHandler.getValue("target");
				metaStatementHandler = SystemMetaObject.forObject(object);
			}
			//
			MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
			//分页信息
			final Page<?> page = localPage.get();
			final BoundSql boundSql = (BoundSql)metaStatementHandler.getValue("delegate.boundSql");
			//分页参数作为参数对象parameterObject的一个属性
			final String sql = boundSql.getSql();
			//重写Sql
			final String pageSql = this.buildPageSql(sql, page);
			//重写分页SQL
			metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
			final Connection connection = (Connection)invocation.getArgs()[0];
			//重设分页参数里的总页数等
			this.setPageParameter(sql, connection, mappedStatement, boundSql, page);
			//将执行权交给下一个拦截器
			return invocation.proceed();
			
		}else if(invocation.getTarget() instanceof ResultSetHandler){
			logger.debug("拦截ResultSetHandler处理...");
			Object result = invocation.proceed();
			Page<?> page = localPage.get();
			page.rows = (List)result;
			return result;
		}
		return null;
	}
	/*
	 * 只拦截两种类型
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object target) {
		if((target instanceof StatementHandler) || (target instanceof ResultSetHandler)){
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {
		
	}
	/**
	 * 修改原SQL为分页SQL。
	 * @param sql
	 * @param page
	 * @return
	 */
	private String buildPageSql(String sql, Page<?> page){
		if(StringUtils.isNotBlank(sql)){
			return sql + "  limit " + page.getStartRow() + "," + page.getPageSize();
		}
		return sql;
	}
	/**
	 * 获取总记录
	 * @param sql
	 * @param connection
	 * @param mappedStatement
	 * @param boundSql
	 * @param page
	 */
	private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Page<?> page){
		logger.debug("查询总记录...");
		PreparedStatement totalStmt =  null;
		ResultSet rs = null;
		try {
			final String totalSql = "SELECT COUNT(0) FROM (" + sql + ")";
			totalStmt = connection.prepareStatement(totalSql);
			final BoundSql totalBS = new BoundSql(mappedStatement.getConfiguration(), totalSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			final MetaObject totalBSObject = SystemMetaObject.forObject(totalBS);
			final MetaObject boundSqlObject = SystemMetaObject.forObject(boundSql);
			totalBSObject.setValue("metaParameters", boundSqlObject.getValue("metaParameters"));
			this.setParameters(totalStmt, mappedStatement, totalBS, boundSql.getParameterObject());
			rs = totalStmt.executeQuery();
			int totalCount = 0;
			if(rs.next()){
				totalCount = rs.getInt(1);
			}
			page.setTotal(Math.max(totalCount, 0));
		} catch (Exception e) {
			logger.warn("ignore this exception", e);
		}finally{
			try {
				if(rs != null) rs.close();
			} catch (Exception e) {
				logger.warn("查询总记录后,关闭ResultSet异常:" + e.getMessage(), e);
			}
			try {
				if(totalStmt != null) totalStmt.close();
			} catch (Exception e) {
				logger.warn("查询总记录后,关闭PreparedStatement异常:" + e.getMessage(), e);
			}
		}
	}
	//代入参数
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws Exception{
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
		parameterHandler.setParameters(ps);
	}

	/**
	 * 分页对象。
	 * @param <E>
	 * @author jeasonyoung
	 * @since 2015年8月10日
	 */
	public static class Page<E>{
		private int pageIndex, pageSize, startRow, total;
		private List<E> rows;
		/**
		 * 构造函数。
		 * @param pageIndex
		 * 页码。
		 * @param pageSize
		 * 页数据量。
		 */
		public Page(int pageIndex, int pageSize){
			//当前页码
			this.pageIndex = Math.max(pageIndex, 0);
			//页数据量
			this.pageSize = Math.max(pageSize, 0);
			//开始行
			this.startRow = (this.pageIndex > 0 ? (this.pageIndex - 1) * this.pageSize : 0);
		}
		/**
		 * 获取当前页码。
		 * @return 当前页码。
		 */
		public int getPageIndex() {
			return pageIndex;
		}
		/**
		 * 获取页数据行。
		 * @return 页数据行。
		 */
		public int getPageSize() {
			return pageSize;
		}
		/**
		 * 获取总数据行数。
		 * @return total
		 */
		public int getTotal() {
			return total;
		}
		/**
		 * 设置总数据行数。
		 * @param total
		 * 总数据行数。
		 */
		private void setTotal(int total){
			this.total = total;
		}
		/**
		 * 获取开始行。
		 * @return startRow
		 */
		public int getStartRow() {
			return startRow;
		}
		/**
		 * 获取总页数。
		 * @return 总页数。
		 */
		public int getPages() {
			if(this.total > 0){
				if(this.pageSize == 0) return 1;
				return (this.total / this.pageSize) + ((this.total % this.pageSize == 0) ? 0 : 1);
			}
			return 0;
		}
		/**
		 * 获取当前页数据集合。
		 * @return 当前页数据集合。
		 */
		public List<E> getRows() {
			return rows;
		}
	}
}