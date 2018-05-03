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
 * <p>ClassName: DBConn </p> <p>Description: �������ݿ����ļ� </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft </p> @Database: LIS
 * @CreateDate��2002-08-09
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

	public CErrors mErrors = new CErrors(); // ������Ϣ

	private javax.naming.Context cxt;

	private javax.sql.DataSource ds;

	// @Method
	/**
	 * ��������
	 * @return boolean
	 */
	public boolean createConnection()
	{
		int dbType = 0;
		/*
		 * ����ķ��������ӱ��������ж�ȡ��ʱ��û�а�bIsPool����Ϊfalse
		 * ����ÿ��ִ�еľ���con.close()������û���𵽳ص����á� ������������ӳ���������û�п��е����ӣ��ӱ�����ȥ���Ӿ�Υ���˹���
		 * dbtype=10û������
		 */
		// WebLogic���ӳ����õ���
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
				// ����ȡ���ӳ�ʧ��ʱ���ӱ������Ӷ�ȡ����
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
		// apache���ӳ����õ���
		if (JUrl.getDBType().toUpperCase().equals("COMMONSDBCP"))
		{
			// System.out.println("ִ��apache�����ѯ���ݿ�");
			bIsPool = true;
			if (getApachecommonDBCP())
			{
				// System.out.println("dbcp connection create successful");
				return true;
			}
			else
			{
				System.out.println("û�����ӵ����ݿ�");
				return false;
			}
		}
		// �����Լ���д��jdbc����
		try
		{
			if (con != null)
			{
				if (!con.isClosed())
				{ // timeout
					try
					{ // Ϊ�˽������ʱ�������⣬�ڷ���֮ǰ��������һ��con
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

					// @@������
					CError tError = new CError();
					tError.moduleName = "DBConn";
					tError.functionName = "createConnection";
					tError.errorMessage = "Ŀǰ�ݲ�֧�ִ������͵����ݿ�!";
					this.mErrors.addOneError(tError);

					return false;
			} // end of switch
		}
		catch (Exception e)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "DBConn";
			tError.functionName = "createConnection";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		// �����������ݿ�
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
			// @@������
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
	 * ����Weblogic���ӳ�
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
			/* weblogic�����ӳ���д��close()���� */
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
	 * ��apache�ṩ�����ӳ���ȡ���ӣ�ʧ�ܷ���false
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
				// ������ӵ���Oracle���ݿ⣬��Ҫ��΢����һ�����ڵĸ�ʽ��������ڷ�������������һ�£�������������ĳ���
				// �������һ���ֶ����ͣ��������Ƿ�ʹ����������
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
	 * ʵ����Connection�ӿڵ�close()������û�������ĶϿ����ӣ� ���ǽ����ӷŻ����ӳأ���bNotInUse���ó�δʹ�á�
	 * @throws SQLException
	 */
	public void close() throws SQLException
	{
		if (bIsPool)
		{ // �����ͨ��weblogic���ӳصõ�����
			// �˴���close()�����ǽ����ӷŻ�weblogic���ӳء�
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
