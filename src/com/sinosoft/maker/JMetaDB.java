/*
 * <p>ClassName: JMetaDB </p>
 * <p>Description: 获得数据库中的表信息和字段信息 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @author: HST
 * @version: 1.0
 * @date: 2002-05-31
 */
package com.sinosoft.maker;

import java.sql.*;
import java.util.Vector;

import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.JdbcUrl;

public class JMetaDB
{
	// @Constructor
	public JMetaDB(JdbcUrl sUrl)
	{
		JUrl = sUrl;
	}

	// @Field
	private final JdbcUrl JUrl;

	private Vector Fields;

	private Vector Tables;

	// @Method
	public String getJUrl()
	{
		return JUrl.getJdbcUrl();
	}

	public Vector getPrimaryKey(Vector fields, String strTableCode) throws Exception
	{
		PreparedStatement pstmt = null;
		Connection con = null;
		// Statement stmt = null;
		DatabaseMetaData dbmd = null;
		ResultSetMetaData rsmd = null;
		ResultSet pkRS = null;
		// System.out.println("get primary key");
		// 尝试连接数据库
		con = DBConnPool.getConnection();
		// System.out.println("after connection");
		if (con == null)
		{
			System.out.println("Connect failed!");
			System.exit(0);
		}

		try
		{
			pstmt = con.prepareStatement("select * from " + strTableCode + " where rownum <= 1", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			pkRS = pstmt.executeQuery();

			rsmd = pkRS.getMetaData();
			int n2 = rsmd.getColumnCount();
			int n = fields.size();
			if (n != n2)
			{
				System.out.println("n = " + n);
				System.out.println("n2 = " + n2);
				System.out.println("表结构字段个数不统一，请查询！");
				con.close();
				throw new Exception("表结构字段个数不统一，请查询！");
			}
			else
			{
				// 除了个数的校验，还需要添加字段顺序的校验，这样就可以在后面的获取中使用索引而非字段
				for (int m = 1; m <= n; m++)
				{
					FieldInfo f = (FieldInfo) fields.get(m - 1);
					String tPdmColumnName = f.getFieldCode(); // pdm中字段
					String tDataColumnName = rsmd.getColumnName(m); // 数据库中字段
					// 设置数据库字段的类型及长度
					rsmd.getColumnName(m);
					f.setFieldSQLType(rsmd.getColumnType(m));
					f.setFieldLength(rsmd.getColumnDisplaySize(m));

					// 如果同顺位的字段不同名，则表示pdm中的表结构顺序＆库中不同，需要查证。
					if (!tPdmColumnName.equalsIgnoreCase(tDataColumnName))
					{
						System.out.println(strTableCode + "表在pdm中的字段" + tPdmColumnName + "和库中该字段位置不同");
						con.close();
						throw new Exception(strTableCode + "表在pdm中的字段" + tPdmColumnName + "和库中该字段位置不同");
					}
					// System.out.println(FieldCode + " - pdm中的字段");
					// System.out.println(rsmd.getColumnName(m) + " - 数据库中的字段");
				}

				dbmd = con.getMetaData();
				strTableCode = strTableCode.toUpperCase();
				// 纯粹的不晓得，第二个参数的用法，db2的话，没有第二个参数才可以正常的得到pk
				// pkRS = dbmd.getPrimaryKeys(null, null, strTableCode);
				JdbcUrl tJdbcUrl = new JdbcUrl();
				pkRS = dbmd.getPrimaryKeys(null,tJdbcUrl.getUserName().toUpperCase(), strTableCode);

				while (pkRS.next())
				{
					for (int i = 0; i < n; i++)
					{
						FieldInfo f = (FieldInfo) fields.get(i);
						String FieldCode = f.getFieldCode();

//						rsmd.getColumnName(i + 1);
//						f.setFieldSQLType(rsmd.getColumnType(i + 1));
//						f.setFieldLength(rsmd.getColumnDisplaySize(i + 1));

						if (FieldCode.toLowerCase().equals(pkRS.getString(4).toLowerCase()))
						{
							f.setFieldProperty("PK");
							fields.remove(i);
							fields.add(i, f);
						}
					} // end of for
				} // end of while
			}
		} // end of try
		catch (Exception e1)
		{
			e1.printStackTrace();
			con.close();
			throw e1;
		}
		pkRS.close();

		// 断开数据库连接
		con.close();

		return fields;
	}

	public Vector getFields(String strTableCode) throws Exception
	{
		Connection con = null;
		Statement stmt = null;
		DatabaseMetaData dbmd = null;
		ResultSet tableRS = null;
		ResultSet pkRS = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;

		strTableCode = strTableCode.toLowerCase();

		// 尝试连接数据库
		con = DBConnPool.getConnection();
		if (con == null)
		{
			System.out.println("Connect failed!");
			System.exit(0);
		}

		try
		{
			stmt = con.createStatement();
			dbmd = con.getMetaData();
			tableRS = dbmd.getTables(null, null, null, new String[]
			{ "TABLE" });
			Fields = new Vector();

			while (tableRS.next())
			{
				if (!(tableRS.getString(4).equals("TABLE") && strTableCode.equals(tableRS.getString(3).toLowerCase())))
				{
					continue;
				}

				rs = stmt.executeQuery("select * from " + strTableCode);
				rsmd = rs.getMetaData();

				for (int i = 1; i <= rsmd.getColumnCount(); i++)
				{
					FieldInfo f = new FieldInfo();
					// 加入字段名称
					f.setFieldCode(rsmd.getColumnName(i));

					// 加入字段类型
					/*
					 * for informix char--1 decimal--3 int--4 serial --4
					 * smallint --5 varchar--12 date--91 datetime year to second
					 * --93
					 */
					switch (rsmd.getColumnType(i))
					{
						case 1:
						case 12:
						case 91:
						case 93:
							f.setFieldType("String");
							break;
						case 3:
							f.setFieldType("float");
							break;
						case 4:
						case 5:
							f.setFieldType("int");
							break;
						default:
							f.setFieldType("String");
							break;
					} // end of switch

					// 2005-11-11 Kevin
					f.setFieldSQLType(rsmd.getColumnType(i));
					f.setFieldLength(rsmd.getColumnDisplaySize(i));

					Fields.addElement(f);
				} // end of for

				// 处理主键标记
				pkRS = dbmd.getPrimaryKeys(null, null, strTableCode);
				int n = Fields.size();
				while (pkRS.next())
				{
					for (int i = 0; i < n; i++)
					{
						FieldInfo f = (FieldInfo) Fields.get(i);
						String FieldCode = f.getFieldCode();
						if (FieldCode.toLowerCase().equals(pkRS.getString(4).toLowerCase()))
						{
							f.setFieldProperty("PK");
							Fields.remove(i);
							Fields.add(i, f);
						}
					} // end of for
				} // end of while
			} // end of while
		} // end of try
		catch (Exception e1)
		{
			e1.printStackTrace();
			con.close();
			throw e1;
		}

		// 断开数据库连接
		con.close();

		return Fields;
	}

	public Vector getTables() throws Exception
	{
		Connection con = null;
		DatabaseMetaData dbmd = null;
		ResultSet tableRS = null;

		// 尝试连接数据库
		con = DBConnPool.getConnection();
		if (con == null)
		{
			System.out.println("Connect failed!");
			System.exit(0);
		}

		// 查询字段信息
		try
		{
			dbmd = con.getMetaData();
			tableRS = dbmd.getTables(null, null, null, new String[]
			{ "TABLE" });
			Tables = new Vector();

			while (tableRS.next())
			{
				if (tableRS.getString(4).equals("TABLE"))
				{
					String t = tableRS.getString(3);
					t = t.toLowerCase();
					t = t.substring(0, 1).toUpperCase() + t.substring(1);
					Tables.addElement(t);
				}
			} // end of while
		} // end of try
		catch (Exception e1)
		{
			e1.printStackTrace();
			con.close();
			throw e1;
		}

		// 断开数据库连接
		con.close();

		return Tables;
	}
}
