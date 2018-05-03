/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;

import java.io.CharArrayWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

/*
 * <p>ClassName: DBConn </p> <p>Description: 连接数据库类文件 </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft </p> @Database: LIS
 * @CreateDate：2002-08-09
 */
public class DBConn implements java.sql.Connection
{
	// @Field
	private JdbcUrl JUrl;

	private Connection con = null;

	private boolean bNotInUse;

	private boolean bIsPool = false;

	private java.util.Date m_lastestAccess = null;

	private CharArrayWriter m_buf = new CharArrayWriter();

	private PrintWriter m_pw = new PrintWriter(m_buf, true);

	public CErrors mErrors = new CErrors(); // 错误信息

	private javax.naming.Context cxt;

	private javax.sql.DataSource ds;

	// @Method
	/**
	 * 创建连接
	 * @return boolean
	 */
	public boolean createConnection()
	{
		int dbType = 0;
		/*
		 * 这里的方法，当从备份连接中读取得时候，没有把bIsPool重置为false
		 * 这样每次执行的就是con.close()方法，没有起到池的作用。 而且如果是连接池已满而且没有空闲的连接，从备份中去连接就违背了规则。
		 * dbtype=10没有意义
		 */
		// WebLogic连接池配置调用
		if (JUrl.getDBType().toUpperCase().equals("WEBLOGICPOOL"))
		{
			dbType = 10;
			bIsPool = true;
			if (getWeblogicPoolConnection())
			{
				return true;
			}
			else
			{
				// return false;
				// 当读取连接池失败时，从备份连接读取连接
				JdbcUrlBackUp tJdbcUrlBackUp = new JdbcUrlBackUp();
				JUrl.setDBName(tJdbcUrlBackUp.getDBName());
				JUrl.setDBType(tJdbcUrlBackUp.getDBType());
				JUrl.setIP(tJdbcUrlBackUp.getIP());
				JUrl.setPassWord(tJdbcUrlBackUp.getPassWord());
				JUrl.setPort(tJdbcUrlBackUp.getPort());
				JUrl.setServerName(tJdbcUrlBackUp.getServerName());
				JUrl.setUser(tJdbcUrlBackUp.getUserName());
			}
		}
		// apache连接池配置调用
		if (JUrl.getDBType().toUpperCase().equals("COMMONSDBCP"))
		{
			// System.out.println("执行apache服务查询数据库");
			bIsPool = true;
			if (getApachecommonDBCP())
			{
				// System.out.println("dbcp connection create successful");
				return true;
			}
			else
			{
				System.out.println("没有连接到数据库");
				return false;
			}
		}
		// 调用自己编写的jdbc连接
		try
		{
			if (con != null)
			{
				if (!con.isClosed())
				{ // timeout
					try
					{ // 为了解决“超时”的问题，在返回之前，先试用一下con
						Statement stmt = con.createStatement();
						stmt.execute("SELECT * FROM DUAL");
						stmt.close();
						return true;
					}
					catch (Exception e)
					{
						e.printStackTrace();
						System.out.println("DBConn : recreate DBConn");
						con.close();
						con = null;
					}
				}
				con = null;
			}

			if (JUrl.getDBType().toUpperCase().equals("ORACLE"))
			{
				dbType = 1;
			}
			if (JUrl.getDBType().toUpperCase().equals("INFORMIX"))
			{
				dbType = 2;
			}
			if (JUrl.getDBType().toUpperCase().equals("SQLSERVER"))
			{
				dbType = 3;
			}
			if (JUrl.getDBType().toUpperCase().equals("DB2"))
			{
				dbType = 4;
			}

			switch (dbType)
			{
				case 1: // ORACLE
					Class.forName("oracle.jdbc.driver.OracleDriver");
					break;
				case 2: // INFORMIX
					Class.forName("com.informix.jdbc.IfxDriver");
					break;
				case 3: // SQLSERVER
					Class.forName("com.inet.tds.TdsDriver");
					break;
				case 4: // DB2
					Class.forName("com.ibm.db2.jcc.DB2Driver");
					break;
				default:

					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "DBConn";
					tError.functionName = "createConnection";
					tError.errorMessage = "目前暂不支持此种类型的数据库!";
					this.mErrors.addOneError(tError);

					return false;
			} // end of switch
		}
		catch (Exception e)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DBConn";
			tError.functionName = "createConnection";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		// 尝试连接数据库
		try
		{
			switch (dbType)
			{

				case 1:

					// ORACLE
					con = DriverManager.getConnection(JUrl.getJdbcUrl(), JUrl.getUserName(), JUrl.getPassWord());
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					stmt.execute("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS'");
					stmt.close();
					break;

				case 2:

					// INFORMIX
					con = DriverManager.getConnection(JUrl.getJdbcUrl());
					break;

				case 3:

					// SQLSERVER
					con = DriverManager.getConnection(JUrl.getJdbcUrl(), JUrl.getUserName(), JUrl.getPassWord());
					break;
				case 4:

					// DB2
					con = DriverManager.getConnection(JUrl.getJdbcUrl(), JUrl.getUserName(), JUrl.getPassWord());
					break;
			} // end of switch
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DBConn";
			tError.functionName = "createConnection";
			tError.errorMessage = "Connect failed!  error code =" + e.getErrorCode();
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	 * 对于Weblogic连接池
	 * @return boolean
	 */
	private boolean getWeblogicPoolConnection()
	{
		JdbcUrl tJU = new JdbcUrl();
		try
		{
			// System.out.println("***************Connect to WebLogic Pool
			// ************");
			Driver myDriver = (Driver) (Class.forName("weblogic.jdbc.pool.Driver").newInstance());
			/* weblogic的连接池重写了close()方法 */
			con = myDriver.connect(tJU.getJdbcUrl(), null);
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.execute("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS'");
			stmt.close();
		}
		catch (Exception ex)
		{
			System.out.println("$$$$$$$$WebLogicPool Connect Failed$$$$$");
			return false;
		}

		return true;
	}

	/**
	 * 从apache提供的连接池中取连接，失败返回false
	 * @return boolean
	 * @date:
	 */
	private boolean getApachecommonDBCP()
	{
		JdbcUrl tJU = new JdbcUrl();
		try
		{
			Context initCtx = new InitialContext();
			cxt = (Context) initCtx.lookup("java:comp/env");
			Object obj = (Object) cxt.lookup(tJU.getDBName());
			ds = (javax.sql.DataSource) obj;
			if (ds != null)
			{
				con = ds.getConnection();
				// 如果连接的是Oracle数据库，需要稍微处理一下日期的格式，最好是在服务器哪里设置一下，而不调用下面的程序
				// 可以添加一个字段类型，来控制是否使用下面的语句
				if (con != null)
				{
					// Statement stmt = con.createStatement(ResultSet.
					// TYPE_SCROLL_SENSITIVE,
					// ResultSet.CONCUR_UPDATABLE);
					// stmt.execute(
					// "alter session set nls_date_format = 'YYYY-MM-DD
					// HH24:MI:SS'");
					// stmt.close();
					return true;
				}
				return false;
			}
			else
			{
				System.out.println("a error occured when geting datasource");
				return false;
			}
		}
		catch (Throwable e)
		{
			System.out.println("failure when connect apache commons dbcp ");
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * kevin 2002-10-04 friend function used by DBConnPool
	 */
	protected DBConn()
	{
		JUrl = new JdbcUrl();

		bNotInUse = true;
	}

	protected boolean isInnerClose()
	{
		try
		{
			if (con == null)
			{
				return true;
			}

			return con.isClosed();
		}
		catch (SQLException ex)
		{
			return true;
		}
	}

	protected void setInUse()
	{
		/**
		 * Record stack information when each connection is get We reassian
		 * System.err, so Thread.currentThread().dumpStack() can dump stack info
		 * into our class FilterPrintStream.
		 */
		// new Throwable().printStackTrace();
		new Throwable().printStackTrace(m_pw);

		bNotInUse = false;

		/**
		 * record lastest access time
		 */
		setLastestAccess();
	}

	// is this dbconn been used by someone
	protected boolean isInUse()
	{
		return !bNotInUse;
	}

	/*
	 * kevin 2002-10-04 Note: JDK 1.3 implements of java.sql.Connection
	 */
	public void clearWarnings() throws SQLException
	{
		con.clearWarnings();
	}

	/**
	 * 实现了Connection接口的close()方法，没有真正的断开连接， 而是将连接放回连接池，将bNotInUse设置成未使用。
	 * @throws SQLException
	 */
	public void close() throws SQLException
	{
		if (bIsPool)
		{ // 如果是通过weblogic连接池得到连接
			// 此处的close()方法是将连接放回weblogic连接池。
			con.close();
		}
		else
		{
			// System.out.println("DBConn : user release connection!");
			m_buf.reset(); // clear stack info of connection
			bNotInUse = true;
		}
	}

	public void commit() throws SQLException
	{
		con.commit();
	}

	public Statement createStatement() throws SQLException
	{
		return con.createStatement();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException
	{
		return con.createStatement(resultSetType, resultSetConcurrency);
	}

	// /* JDK 1.4
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
	{
		try
		{
			return con.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	// */

	public boolean getAutoCommit() throws SQLException
	{
		return con.getAutoCommit();
	}

	public String getCatalog() throws SQLException
	{
		return con.getCatalog();
	}

	// /* JDK 1.4
	public int getHoldability()
	{
		try
		{
			return con.getHoldability();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

	// */

	public DatabaseMetaData getMetaData() throws SQLException
	{
		return con.getMetaData();
	}

	public int getTransactionIsolation() throws SQLException
	{
		return con.getTransactionIsolation();
	}

	public Map getTypeMap() throws SQLException
	{
		return con.getTypeMap();
	}

	public SQLWarning getWarnings() throws SQLException
	{
		return con.getWarnings();
	}

	public boolean isClosed() throws SQLException
	{
		if (bNotInUse)
		{
			return true;
		}
		else
		{
			return con.isClosed();
		}
	}

	public boolean isReadOnly() throws SQLException
	{
		return con.isReadOnly();
	}

	public String nativeSQL(String sql) throws SQLException
	{
		return con.nativeSQL(sql);
	}

	public CallableStatement prepareCall(String sql) throws SQLException
	{
		return con.prepareCall(sql);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
	{
		return con.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	// /* JDK 1.4
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability)
	{
		try
		{
			return con.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	// */

	public PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return con.prepareStatement(sql);
	}

	// /* JDK 1.4
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
	{
		try
		{
			return con.prepareStatement(sql, autoGeneratedKeys);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
	{
		try
		{
			return con.prepareStatement(sql, columnIndexes);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	// */

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException
	{
		return con.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	// /* JDK 1.4
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability)
	{
		try
		{
			return con.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames)
	{
		try
		{
			return con.prepareStatement(sql, columnNames);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public void releaseSavepoint(Savepoint savepoint)
	{
		try
		{
			con.releaseSavepoint(savepoint);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

	// */

	public void rollback() throws SQLException
	{
		con.rollback();
	}

	// /* JDK 1.4
	public void rollback(Savepoint savepoint)
	{
		try
		{
			con.rollback(savepoint);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

	// */

	public void setAutoCommit(boolean autoCommit) throws SQLException
	{
		con.setAutoCommit(autoCommit);
	}

	public void setCatalog(String catalog) throws SQLException
	{
		con.setCatalog(catalog);
	}

	// /* JDK 1.4
	public void setHoldability(int holdability)
	{
		try
		{
			con.setHoldability(holdability);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

	// */

	public void setReadOnly(boolean readOnly) throws SQLException
	{
		con.setReadOnly(readOnly);
	}

	// /* JDK 1.4
	public Savepoint setSavepoint()
	{
		try
		{
			return con.setSavepoint();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public Savepoint setSavepoint(String name)
	{
		try
		{
			return con.setSavepoint(name);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	// */

	public void setTransactionIsolation(int level) throws SQLException
	{
		con.setTransactionIsolation(level);
	}

	public void setTypeMap(Map map) throws SQLException
	{
		con.setTypeMap(map);
	}

	protected void dumpConnInfo(OutputStream os) throws Exception
	{
		// If this connection hasn't been closed, dump its' stack info
		if (this.isClosed() == false)
		{
			os.write(m_buf.toString().getBytes());
		}
	}

	protected void setLastestAccess()
	{
		m_lastestAccess = new java.util.Date();
	}

	protected java.util.Date getLastestAccess()
	{
		return m_lastestAccess;
	}

	protected void innerClose()
	{
		System.out.println("DBConn.innerClose");
		if (isInUse())
		{
			return;
		}

		m_lastestAccess = null;

		try
		{
			con.rollback();
			con.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			con = null;
		}
	}

	public <T> T unwrap(Class<T> iface) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}



	public Clob createClob() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Blob createBlob() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public NClob createNClob() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML createSQLXML() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValid(int timeout) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void setClientInfo(String name, String value) throws SQLClientInfoException
	{
		// TODO Auto-generated method stub
		
	}

	public void setClientInfo(Properties properties) throws SQLClientInfoException
	{
		// TODO Auto-generated method stub
		
	}

	public String getClientInfo(String name) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Properties getClientInfo() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
