package com.sinosoft.utility;

/**
 * <p>
 * ClassName: JdbcUrlBackUp
 * </p>
 * <p>
 * Description: 构建 Jdbc 的 备份url
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
public class JdbcUrlBackUp
{
	// @Constructor
	public JdbcUrlBackUp()
	{
		// WebLogic连接池，其中MyPool为连接池的名称
		// DBType = "WEBLOGICPOOL";
		// DBName = "MyPool";

		/*
		 * DBType = "INFORMIX"; IP = "172.16.120.201"; Port = "12000"; DBName =
		 * "lis"; ServerName = "server_intel"; UserName = "informix"; PassWord =
		 * "informix";
		 */

		// z
		DBType = "ORACLE";
		IP = "10.0.22.129";
		Port = "1521";
		DBName = "testdb";
		UserName = "lis";
		PassWord = "lis";

		// 5.7
		// DBType = "ORACLE";
		// IP = "10.0.5.7";
		// Port = "1531";
		// DBName = "lisdb";
		// UserName = "lisbak";
		// PassWord = "lisbak";

		// 9080
		// DBType = "ORACLE";
		// IP = "10.0.22.136";
		// Port = "1521";
		// DBName = "lisdb";
		// UserName = "lis_test";
		// PassWord = "lis_test";

		// 136l
		// DBType = "ORACLE";
		// IP = "10.0.22.136";
		// Port = "1521";
		// DBName = "lisdb";
		// UserName = "lis";
		// PassWord = "lis";

		// New
		// DBType = "ORACLE";
		// IP = "10.0.22.136";
		// Port = "1521";
		// DBName = "lisdb";
		// UserName = "lis_new";
		// PassWord = "lis_new";

		// 8990
		// DBType = "ORACLE";
		// IP = "10.0.22.136";
		// Port = "1521";
		// DBName = "lisdb";
		// UserName = "lis_new";
		// PassWord = "lis_new";

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
			sUrl = "jdbc:oracle:thin:@" + IP + ":" + Port + ":" + DBName;
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
		if (DBType.trim().toUpperCase().equals("WEBLOGICPOOL"))
		{
			sUrl = "jdbc:weblogic:pool:" + DBName;
		}

		return sUrl;
	}
}
