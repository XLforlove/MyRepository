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
		ArrayList clumBandNameList = new ArrayList(); // ��Ű󶨱���������
		String[] temp = sql.split(":"); // ����ѱ��������ָ�ȡ����
		for (int i = 1; i < temp.length; i++)
		{
			clumBandNameList.add(temp[i].substring(0, temp[i].indexOf(" ")));
		}
		sql = sql.replaceAll(":(.*?)\\s", "?");// �Ѱ󶨱���������:XXX ȡ��Ϊ ?,���ɱ�׼SQL

		stmt = con.prepareStatement(sql);

		int index = 0;
		for (int i = 0; i < clumBandNameList.size(); i++)
		{
			Object value = map.get(clumBandNameList.get(i));
			System.out.println("�������ֵ:" + clumBandNameList.get(i) + "=" + value.toString());
			String type = value.getClass().getName().substring(value.getClass().getName().lastIndexOf(".")); // ��ȡ�󶨵�����
			System.out.println("�󶨱��������ͣ�" + type);
			index = i + 1; // �󶨵�����
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
			// ������ڲ鿴SQL��־�����԰�JDBC������һ��
			String sql = "INSERT INTO PERSON(PERSON_ID,AGE,FIRSTNAME,LASTNAME,STATE) VALUES(:PERSON_ID   ,:AGE   ,:FIRSTNAME ,:FIRSTNAME ,:STATE )";
			sql = "select * from ldcode where codetype = :CODETYPE and code = :CODE";

			HashMap map = new HashMap();
			map.put("CODETYPE", "sex");
			map.put("CODE", "1");
			// map.put("FIRSTNAME", "����");
			// map.put("STATE", 1.111);

			PreparedStatement p = getStatementValues(conn, sql, map);
			ResultSet rs = p.executeQuery();
			SSRS tSSRS = null;
			ResultSetMetaData rsmd = rs.getMetaData();

			int n = rsmd.getColumnCount();
			tSSRS = new SSRS(n);

			int k = 0;

			// ȡ���ܼ�¼��

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
			// ��������Ϊ�ַ�
			if ((dataType == Types.CHAR) || (dataType == Types.VARCHAR))
			{
				// ���ڴ������ݿ��������GBKģʽ�����û�б�Ҫ��һ��unicodeToGBK
				// strValue = StrTool.unicodeToGBK(rs.getString(i));
				strValue = rs.getString(i);
			}
			// ��������Ϊ���ڡ�ʱ��
			else if ((dataType == Types.TIMESTAMP) || (dataType == Types.DATE))
			{
//				strValue = fDate.getString(rs.getDate(i));
			}
			// ��������Ϊ����
			else if ((dataType == Types.DECIMAL) || (dataType == Types.FLOAT))
			{
				// strValue = String.valueOf(rs.getFloat(i));
				// ��������ķ���ʹ�����������ʱ�򲻻������ѧ��������ʽ
				strValue = String.valueOf(rs.getBigDecimal(i));
				// ȥ�㴦��
				strValue = PubFun.getInt(strValue);
			}
			// ��������Ϊ����
			else if ((dataType == Types.INTEGER) || (dataType == Types.SMALLINT))
			{
				strValue = String.valueOf(rs.getInt(i));
				strValue = PubFun.getInt(strValue);
			}
			// ��������Ϊ����
			else if (dataType == Types.NUMERIC)
			{
				if (dataScale == 0)
				{
					if (dataPrecision == 0)
					{
						// strValue = String.valueOf(rs.getDouble(i));
						// ��������ķ���ʹ�����������ʱ�򲻻������ѧ��������ʽ
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
					// ��������ķ���ʹ�����������ʱ�򲻻������ѧ��������ʽ
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
