import java.io.StringReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * @author not attributable
 * @version 1.0
 */
public class Test
{
	public Test()
	{}

	public static PreparedStatement getStatementValues(Connection con, String sql, HashMap map) throws SQLException
	{
		PreparedStatement stmt = null;
		ArrayList clumBandNameList = new ArrayList(); // 存放绑定变量的名字
		String[] temp = sql.split(":"); // 这里把变量的名字给取出来
		for (int i = 1; i < temp.length; i++)
		{
			clumBandNameList.add(temp[i].substring(0, temp[i].indexOf(" ")));
		}
		sql = sql.replaceAll(":(.*?)\\s", "?");// 把绑定变量的名字:XXX 取出为 ?,生成标准SQL

		stmt = con.prepareStatement(sql);

		int index = 0;
		for (int i = 0; i < clumBandNameList.size(); i++)
		{
			Object value = map.get(clumBandNameList.get(i));
			System.out.println("邦定变量的值:" + clumBandNameList.get(i) + "=" + value.toString());
			String type = value.getClass().getName().substring(value.getClass().getName().lastIndexOf(".")); // 获取绑定的类型
			System.out.println("绑定变量的类型：" + type);
			index = i + 1; // 绑定的索引
			if (type.equalsIgnoreCase("String"))
			{
				String content = value.toString();
				if (content.length() > 2000)
				{
					stmt.setCharacterStream(index, new StringReader(content), content.length());
				}
				else
				{
					stmt.setString(index, content);
				}
			}
			else if (type.equalsIgnoreCase("Short"))
			{
				stmt.setShort(index, Short.parseShort(value.toString()));
			}
			else if (type.equalsIgnoreCase("Integer"))
			{
				stmt.setInt(index, Integer.parseInt(value.toString()));
			}
			else if (type.equalsIgnoreCase("Float"))
			{
				stmt.setFloat(index, Float.parseFloat(value.toString()));
			}
			else if (type.equalsIgnoreCase("Byte"))
			{
				stmt.setByte(index, Byte.parseByte(value.toString()));
			}
			else if (type.equalsIgnoreCase("Char"))
			{
				stmt.setString(index, value.toString());

			}
			else if (type.equalsIgnoreCase("Long"))
			{
				stmt.setLong(index, Long.parseLong(value.toString()));
			}
			else if (type.equalsIgnoreCase("Double"))
			{
				stmt.setDouble(index, Double.parseDouble(value.toString()));
			}
			else if (type.equalsIgnoreCase("Boolean"))
			{
				stmt.setBoolean(index, Boolean.getBoolean(value.toString()));
			}
			else if (type.equalsIgnoreCase("Date"))
			{
				if (value instanceof java.sql.Date)
					stmt.setDate(index, (java.sql.Date) value);
				else
					stmt.setDate(index, java.sql.Date.valueOf(value.toString()));
			}
			else if (type.equalsIgnoreCase("Time"))
			{
				if (value instanceof Time)
					stmt.setTime(index, (Time) value);
				else
					stmt.setTime(index, Time.valueOf(value.toString()));
			}
			else if (type.equalsIgnoreCase("DateTime"))
			{
				if (value instanceof Timestamp)
					stmt.setTimestamp(index, (Timestamp) value);
				else if (value instanceof java.sql.Date)
					stmt.setTimestamp(index, new Timestamp(((java.sql.Date) value).getTime()));
				else
					stmt.setTimestamp(index, Timestamp.valueOf(value.toString()));
			}
			else if (type.equalsIgnoreCase("Timestamp"))
			{
				stmt.setTimestamp(index, (Timestamp) value);
			}
			else if (value instanceof Character)
			{
				stmt.setString(index, value.toString());
			}
			else
			{
				stmt.setObject(index, value);
			}
		}
		return stmt;

	}

	public static void main(String[] args)
	{
		try
		{
			// Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Connection conn =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
			// "root", "admin");
			Connection conn = DBConnPool.getConnection();
			conn.setAutoCommit(false);
			// 这里便于查看SQL日志，所以把JDBC给代理一下
			String sql = "INSERT INTO PERSON(PERSON_ID,AGE,FIRSTNAME,LASTNAME,STATE) VALUES(:PERSON_ID   ,:AGE   ,:FIRSTNAME ,:FIRSTNAME ,:STATE )";
			sql = "select * from ldcode where codetype = :CODETYPE and code = :CODE";

			HashMap map = new HashMap();
			map.put("CODETYPE", "sex");
			map.put("CODE", "1");
			// map.put("FIRSTNAME", "张三");
			// map.put("STATE", 1.111);

			PreparedStatement p = getStatementValues(conn, sql, map);
			ResultSet rs = p.executeQuery();
			SSRS tSSRS = null;
			ResultSetMetaData rsmd = rs.getMetaData();

			int n = rsmd.getColumnCount();
			tSSRS = new SSRS(n);

			int k = 0;

			// 取得总记录数

			// Kevin 2006-08-15
			while (rs.next())
			{

				k++;

				for (int j = 1; j <= n; j++)
				{
					tSSRS.SetText(getDataValue(rsmd, rs, j));
				}
			}
			
			System.out.println(tSSRS.encode());
			conn.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

	public static String getDataValue(ResultSetMetaData rsmd, ResultSet rs, int i)
	{
		String strValue = "";

		try
		{
			int dataType = rsmd.getColumnType(i);
			int dataScale = rsmd.getScale(i);
			int dataPrecision = rsmd.getPrecision(i);
			// 数据类型为字符
			if ((dataType == Types.CHAR) || (dataType == Types.VARCHAR))
			{
				// 由于存入数据库的数据是GBK模式，因此没有必要做一次unicodeToGBK
				// strValue = StrTool.unicodeToGBK(rs.getString(i));
				strValue = rs.getString(i);
			}
			// 数据类型为日期、时间
			else if ((dataType == Types.TIMESTAMP) || (dataType == Types.DATE))
			{
//				strValue = fDate.getString(rs.getDate(i));
			}
			// 数据类型为浮点
			else if ((dataType == Types.DECIMAL) || (dataType == Types.FLOAT))
			{
				// strValue = String.valueOf(rs.getFloat(i));
				// 采用下面的方法使得数据输出的时候不会产生科学计数法样式
				strValue = String.valueOf(rs.getBigDecimal(i));
				// 去零处理
				strValue = PubFun.getInt(strValue);
			}
			// 数据类型为整型
			else if ((dataType == Types.INTEGER) || (dataType == Types.SMALLINT))
			{
				strValue = String.valueOf(rs.getInt(i));
				strValue = PubFun.getInt(strValue);
			}
			// 数据类型为浮点
			else if (dataType == Types.NUMERIC)
			{
				if (dataScale == 0)
				{
					if (dataPrecision == 0)
					{
						// strValue = String.valueOf(rs.getDouble(i));
						// 采用下面的方法使得数据输出的时候不会产生科学计数法样式
						strValue = String.valueOf(rs.getBigDecimal(i));
					}
					else
					{
						strValue = String.valueOf(rs.getLong(i));
					}
				}
				else
				{
					// strValue = String.valueOf(rs.getDouble(i));
					// 采用下面的方法使得数据输出的时候不会产生科学计数法样式
					strValue = String.valueOf(rs.getBigDecimal(i));
				}
				strValue = PubFun.getInt(strValue);
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}

		return StrTool.cTrim(strValue);
	}
}
