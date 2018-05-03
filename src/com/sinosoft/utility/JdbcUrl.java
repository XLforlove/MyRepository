package com.sinosoft.utility;

/**
 * <p>
 * ClassName: JdbcUrl
 * </p>
 * <p>
 * Description: ¹¹½¨ Jdbc µÄ url
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @author: HST
 * @version: 1.0
 * @date: 2002-05-31
 */
public class JdbcUrl
{
	// @Constructor
	public JdbcUrl()
	{
		// DBType = "ORACLE";
		// IP = "10.100.11.126";
		// Port = "1521";
		// DBName = "ht";
		// UserName = "reinsur";
		// PassWord = "reinsur";

		DBType = "ORACLE";
		IP = "10.1.82.39";
		Port = "1521";
		DBName = "orcl";
		UserName = "lis";
		PassWord = "lis";
		
//		DBType = "ORACLE";
//		IP = "10.1.82.39";
//		Port = "1521";
//		DBName = "orcl";
//		UserName = "jkx_sh";
//		PassWord = "jkx_sh";

//		DBType = "ORACLE";
//		IP = "10.1.1.100";
//		Port = "1521";
//		DBName = "ora10g";
//		UserName = "jkx_sh";
//		PassWord = "jkxsh123";
	}

	// @Field
	private String DBType;

	private String IP;

	private String Port;

	private String DBName;

	private String ServerName;

	private String UserName;

	private String PassWord;

	// @Method
	public String getDBType()
	{
		return DBType;
	}

	public String getIP()
	{
		return IP;
	}

	public String getPort()
	{
		return Port;
	}

	public String getDBName()
	{
		return DBName;
	}

	public String getServerName()
	{
		return ServerName;
	}

	public String getUserName()
	{
		return UserName;
	}

	public String getPassWord()
	{
		return PassWord;
	}

	public void setDBType(String aDBType)
	{
		DBType = aDBType;
	}

	public void setIP(String aIP)
	{
		IP = aIP;
	}

	public void setPort(String aPort)
	{
		IP = aPort;
	}

	public void setDBName(String aDBName)
	{
		DBName = aDBName;
	}

	public void setServerName(String aServerName)
	{
		ServerName = aServerName;
	}

	public void setUser(String aUserName)
	{
		UserName = aUserName;
	}

	public void setPassWord(String aPassWord)
	{
		PassWord = aPassWord;
	}

	public String getJdbcUrl()
	{
		String sUrl = "";

		if (DBType.trim().toUpperCase().equals("ORACLE"))
		{
			sUrl = "jdbc:oracle:thin:@" + IP + ":" + Port + ":" + getDBName();
		}

		if (DBType.trim().toUpperCase().equals("INFORMIX"))
		{
			sUrl = "jdbc:informix-sqli://" + IP + ":" + Port + "/" + DBName + ":" + "informixserver=" + ServerName
					+ ";" + "user=" + UserName + ";" + "password=" + PassWord + ";";
		}

		if (DBType.trim().toUpperCase().equals("SQLSERVER"))
		{
			sUrl = "jdbc:inetdae:" + IP + ":" + Port + "?sql7=true&" + "database=" + DBName + "&" + "charset=gbk";
		}
		if (DBType.trim().toUpperCase().equals("DB2"))
		{
			sUrl = "jdbc:db2://" + IP + ":" + Port + "/" + DBName;
		}
		// sUrl="jdbc:db2:172.16.120.201:50000/lis;user=db2admin;password=db2";
		return sUrl;
	}
}
